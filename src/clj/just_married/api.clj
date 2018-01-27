(ns just-married.api
  (:gen-class)
  (:require [clojure.string :as string]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [compojure.core :refer [defroutes GET POST]]
            [environ.core :refer [env]]
            [hiccup.core :as html]
            [just-married.shared :refer [available-languages]]
            [just-married.language :refer [detect-language]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :as r-def]
            [ring.middleware.resource :as resources]
            [ring.util.response :as resp]
            [just-married
             [settings :as settings]
             [pages :as pages]
             [db :as db]]))

(defn get-language
  [request]
  (detect-language
   (get-in request [:headers "accept-language"])
   available-languages))

(def pages
  {:guests pages/guest-list
   :home pages/home-page
   :initial pages/initial-page})

(defn- render-page
  [page language]
  (-> (resp/response (html/html ((page pages) language)))
      (resp/content-type "text/html")))

(def ^:private security (= (env :secure) "true"))
(def ^:private default-port 3000)

(defn- get-port
  []
  (Integer. (or (env :port) default-port)))

(defn guest-list
  "Page showing the list of guests, needs to be authenticated"
  [request]
  (if (authenticated? request)
    (render-page :guests :en)
    (throw-unauthorized)))

(def authdata
  "All possible authenticated users"
  {:admin settings/admin-password})

(defn authenticate
  [req {:keys [username password]}]
  (when-let [user-password (get authdata (keyword username))]
    (when (= password user-password)
      (keyword username))))

(def basic-auth-backend
  (http-basic-backend {:realm "andreaenrica.life"
                       :authfn authenticate}))

(defn do-login [{{password "password" next "next"} :params
                 session :session :as req}]
  ;; get the password from an env variable at least
  (if (= password "secure-password")
    (assoc (resp/redirect next "/")
           :session (assoc session :identity "admin"))
    (resp/response "Could not authenticate")))

(defn do-logout [{session :session}]
  (-> (resp/redirect "/login")
      (assoc :session (dissoc session :identity))))

(defn enter-page
  [request]
  (let [language (get-language request)]
    (render-page :initial language)))

(defn main-page
  [request]
  (let [preferred-language (get-language request)
        current-language (-> request :params :language keyword)]

    (if (nil? current-language)
      (resp/redirect (format "/main?language=%s" (name preferred-language)))
      (render-page :home preferred-language))))

(defroutes app-routes
  (GET "/" request (enter-page request))
  (GET "/main" request (main-page request))
  (GET "/guests" request (guest-list request)))

(def app
  (-> app-routes
      (resources/wrap-resource "public")
      (r-def/wrap-defaults (if security
                             r-def/secure-site-defaults
                             (assoc-in r-def/site-defaults [:security :anti-forgery] false)))

      (wrap-authorization basic-auth-backend)
      (wrap-authentication basic-auth-backend)))

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))

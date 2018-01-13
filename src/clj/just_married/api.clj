(ns just-married.api
  (:gen-class)
  (:require [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [hiccup.core :as html]
            [ring.middleware.defaults :as r-def]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [compojure.core :refer [defroutes GET POST]]
            [environ.core :refer [env]]
            [just-married
             [settings :as settings]
             [pages :as pages]
             [db :as db]]))

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
(def ^:private default-language :en)
(def ^:private available-languages #{:en :it})

(defn- get-port
  []
  (Integer. (or (env :port) default-port)))

(defn- detect-language
  "Lookup in the request to find out what should be the default language to serve"
  [request available-languages]
  (let [accept-language (get  (:headers request) "accept-language")
        parsed-languages (map (comp keyword first) (map #(clojure.string/split % #";")
                                                        (clojure.string/split accept-language #",")))
        only-available (filter #(contains? available-languages %) parsed-languages)]

    (or (first only-available) default-language)))

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

(defroutes app-routes
  (GET "/" [] (render-page :initial :en))
  (GET "/enter" [] (render-page :initial :en))
  ;; do a redirect adding the extra information
  (GET "/main" request (render-page :home (detect-language request available-languages)))
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

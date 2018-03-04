(ns just-married.api
  (:gen-class)
  (:require [clojure.walk :refer [keywordize-keys]]
            [clojure.string :as string]
            [clojure.tools.logging :as log]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [compojure.core :refer [defroutes GET POST]]
            [environ.core :refer [env]]
            [hiccup.core :as html]
            [ring.middleware.defaults :as r-def]
            [ring.middleware.resource :as resources]
            [ring.middleware.json :refer [wrap-json-params wrap-json-response]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty]
            [just-married.language :refer [detect-language]]
            [just-married.shared :refer [available-languages]]
            [just-married.pages.home :as home-page]
            [just-married.pages.guests :as guest-page]
            [just-married.pages.enter :as enter]
            [just-married
             [settings :as settings]
             [db :as db]
             [mail :refer [send-email]]]))

(defn get-language
  [request]
  (detect-language
   (get-in request [:headers "accept-language"])
   available-languages))

(def pages
  {:guests  guest-page/guest-list
   :home    home-page/home-page
   :initial enter/initial-page})

(defn- render-page
  [page kwargs]
  (-> (resp/response (html/html ((page pages) kwargs)))
      (resp/content-type "text/html")))

(def ^:private secure? (= (env :secure) "true"))
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
  [request & {:keys [redirect-to]}]
  (let [language (get-language request)]
    (render-page :initial {:language language
                           :redirect-to redirect-to})))

(defn main-page
  [request]
  (let [preferred-language (get-language request)
        current-language (-> request :params :language keyword)]

    (if (nil? current-language)
      (resp/redirect (format "/main?language=%s" (name preferred-language)))
      (render-page :home {:language preferred-language}))))

(defn notify
  [request]
  (let [params
        (-> request
            :params
            ;; this keywordize should not be
            ;; actually needed in theory
            keywordize-keys)]
    (log/info "passing to send-emails parameters"
              params)

    {:status 201
     :body (-> (send-email params)
               :message)}))

(defn mirror
  [request]
  (clojure.pprint/pprint request)
  {:status 201
   :body ""})

(defroutes app-routes
  (GET "/" request (enter-page request))
  (GET "/rvsp" request (enter-page request :redirect-to "rvsp"))
  (POST "/notify" request (notify request))
  ;; do a redirect adding the extra information
  (GET "/main" request (main-page request))
  (POST "/mirror" request (mirror request))
  (GET "/guests" request (guest-list request)))

(def app
  (-> app-routes
      (resources/wrap-resource "public")
      (r-def/wrap-defaults (if secure?
                             r-def/secure-site-defaults
                             (assoc-in r-def/site-defaults [:security :anti-forgery] false)))

      (wrap-authorization basic-auth-backend)
      (wrap-authentication basic-auth-backend)
      wrap-keyword-params
      wrap-json-params))

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))

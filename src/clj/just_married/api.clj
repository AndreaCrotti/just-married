(ns just-married.api
  (:gen-class)
  (:require [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [hiccup.core :as html]
            [ring.middleware.defaults :as r-def]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [compojure.core :refer [defroutes GET POST]]
            [environ.core :refer [env]]
            [just-married.settings :as settings]
            [just-married.pages :as pages]))

(def auth-backend (session-backend))

(def security (= (env :secure) "true"))
(def default-port 3000)

(defn get-port
  []
  (Integer. (or (env :port) default-port)))

(defn- detect-language
  "Lookup in the request to find out what should be the default language to serve"
  [request]
  :en)

(defn guest-list
  "Page showing the list of guests, needs to be authenticated"
  [request]
  (if (authenticated? request)
    (resp/content-type
     {:status 200 :body "Here is your list of guests"}
     "application-json")
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

(defn home
  [language]
  (-> (resp/response (html/html (pages/home-page language)))
      (resp/content-type "text/html")))

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
  (GET "/" request (home (detect-language request)))
  (GET "/en" [] (home :en))
  (GET "/it" [] (home :it))
  ;; (POST "/login" request (do-login request))
  ;; (POST "/logout" request (do-logout request))
  (GET "/guests" request (guest-list request)))

(def app
  (-> app-routes
      (resources/wrap-resource "public")
      (r-def/wrap-defaults (if security
                             r-def/secure-site-defaults
                             (assoc-in r-def/site-defaults [:security :anti-forgery] false)))
      (wrap-authorization basic-auth-backend)
      (wrap-authentication basic-auth-backend)))

;; (assoc-in r-def/site-defaults [:security :anti-forgery] false)

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))

(ns just-married.api
  (:gen-class)
  (:require [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [ring.middleware.defaults :as r-def]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [compojure.core :refer [defroutes GET POST]]
            [environ.core :refer [env]]
            [clostache.parser :as clostache]
            [just-married.settings :as settings]))

(def auth-backend (session-backend))

(def base-vars
  {:google-analytics-id settings/google-analytics-id})

(def text
  {:en
   {:title "Andrea & Enrica"
    :description "Wedding Andrea Crotti and Enrica Verrucci"}

   :it
   {:title "Andrea e Enrica"
    :description "Matrimonio di Andrea Crotti e Enrica Verrucci"}})

(defn render-homepage
  "Render the homepage as string after spitting out a valid index.html
  (which is just really to make figwheel happy)"
  [language]
  (let [rendered
        (clostache/render-resource
         "public/index.moustache"
         (merge
          base-vars
          {:language (name language)}
          (get text language)))]

    (spit "resources/public/index.html" rendered)
    rendered))

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
    {:status 200 :body "Here is your list of guests"}
    {:status 401 :body "I'm sorry you are not allowed to see this"}))

(defn home
  [language]
  (render-homepage language)
  (-> (resp/file-response "index.html"
                          {:root "resources/public"})
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
  (POST "/login" request (do-login request))
  (POST "/logout" request (do-logout request))
  (GET "/guests" request (guest-list request)))

(def app
  (-> app-routes
      (resources/wrap-resource "public")
      (r-def/wrap-defaults (if security
                             r-def/secure-site-defaults
                             (assoc-in r-def/site-defaults [:security :anti-forgery] false)))
      (wrap-authentication auth-backend)))

;; (assoc-in r-def/site-defaults [:security :anti-forgery] false)

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))

(ns just-married.api
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
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
            [just-married.auth :refer [with-basic-auth basic-auth-backend]]
            [just-married.language :refer [get-language]]
            [just-married.pages.home :as home-page]
            [just-married.pages.enter :as enter]
            [just-married.api.labels :refer [labels-api]]
            [just-married.api.rvsp :refer [rvsp!]]
            [just-married.db :as db]))

(def pages
  {:home    home-page/home-page
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

(defn main-page
  [request]
  (let [preferred-language (get-language request)
        current-language (-> request :params :language keyword)]

    (if (nil? current-language)
      (resp/redirect (format "/main?language=%s" (name preferred-language)))
      (render-page :home {:language preferred-language}))))

(defn enter-page
  [request & {:keys [redirect-to]}]
  (let [language (get-language request)]
    (render-page :initial {:language language
                           :redirect-to redirect-to})))

(defn guest-list-api
  [request]
  (let [guests
        (->> (db/all-guests!)
             (map #(select-keys % [:id :first_name :last_name])))]

    (-> {:status 200
         :body   guests}
        (resp/content-type "application/edn"))))

;;TODO: try to use bidi instead for the routing?
(defroutes app-routes
  (GET "/" request (enter-page request))
  (GET "/rvsp" request (enter-page request :redirect-to "rvsp"))
  (GET "/main" request (main-page request))

  (POST "/api/rvsp" request (rvsp! request))
  (GET "/api/labels" request (labels-api request))
  (GET "/api/guests" request (guest-list-api request)))

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

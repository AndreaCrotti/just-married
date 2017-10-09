(ns just-married.api
  (:gen-class)
  (:require [ring.middleware.defaults :as r-def]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [compojure.core :refer [defroutes GET]]
            [environ.core :refer [env]]
            [clostache.parser :as clostache]
            [just-married.settings :as settings]))

(def ENV
  {:google-analytics-id settings/GOOGLE-ANALYTICS-ID})

(def TEXT
  {:en
   {:title "Andrea & Enrica"
    :description "Wedding Andrea Crotti and Enrica Verrucci"}

   :it
   {:title "Andrea e Enrica"
    :description "Matrimonio di Andrea Crotti e Enrica Verrucci"}})

(defn render-homepage
  [language]
  (clostache/render-resource
   "public/index.moustache"
   (merge
    ENV
    {:language (str language)}
    (get TEXT language))))

(def default-port 3000)

(defn get-port
  []
  (Integer. (or (env :port) default-port)))

(def home
  (-> (resp/response (render-homepage :en))
      (resp/content-type "text/html")))

(defroutes app-routes
  (GET "/" [] home))

(def app
  (-> app-routes
      (resources/wrap-resource "public")
      (r-def/wrap-defaults r-def/site-defaults)))

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))

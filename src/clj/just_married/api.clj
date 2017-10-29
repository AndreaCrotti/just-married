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

(def default-port 3000)

(defn get-port
  []
  (Integer. (or (env :port) default-port)))

(defn- detect-language
  "Lookup in the request to find out what should be the default language to serve"
  [request]
  (print "Got request = " request)
  :en)

(defn home
  [language]
  (render-homepage language)
  (-> (resp/file-response "index.html" {:root "resources/public"})
      (resp/content-type "text/html")))

(defroutes app-routes
  (GET "/" request (home (detect-language request)))
  (GET "/en" [] (home :en))
  (GET "/it" [] (home :it)))

(def app
  (-> app-routes
      (resources/wrap-resource "public")
      (r-def/wrap-defaults r-def/site-defaults)))

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))

(ns just-married.api
  (:require [just-married.core :as core]
            [compojure.handler :refer [site]]
            [environ.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.adapter.jetty :as jetty]
            #_[ring.middleware.logger :as logger]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]))

;; (def app
;;   (logger/wrap-with-logger
;;    (wrap-json-response app-routes api-defaults)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

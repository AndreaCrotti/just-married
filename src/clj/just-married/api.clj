(ns just-married.api
  (:require
   [compojure
    [core :refer [GET POST defroutes]]
    [route :as route]]
   [just-married.core :as core]
   [just-married.payment :as payment]
   [compojure.handler :refer [site]]
   [environ.core :refer [env]]
   [ring.middleware.json :refer [wrap-json-response]]
   [ring.adapter.jetty :as jetty]
   [ring.util.http-response :as response]
   [ring.middleware.defaults :refer [api-defaults wrap-defaults]]))

;; (def app
;;   (logger/wrap-with-logger
;;    (wrap-json-response app-routes api-defaults)))

(defroutes app-routes
  (GET "/" [] (response/file-response "index.html" {:root "resources/public"}))
  (POST "/stripe-checkout" [data] (payment/handle-payment data))
  (route/not-found))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

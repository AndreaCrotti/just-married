(ns just-married.api
  (:require
   [compojure
    [core :refer [GET POST defroutes]]
    [route :as route]]
   [just-married.core :as core]
   [just-married.payment :as payment]
   [just-married.settings :as settings]
   [just-married.mail :refer [send-email]]
   [compojure.handler :refer [site]]
   [environ.core :refer [env]]
   [ring.middleware.json :refer [wrap-json-response]]
   [ring.adapter.jetty :as jetty]
   [ring.util.http-response :as response]
   [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
   [raven-clj.ring :refer [wrap-sentry]]))

(defroutes app-routes
  (GET "/" [] (response/file-response "index.html" {:root "resources/public"}))
  (POST "/send-email" request
        (do (print "request =" request)
            (send-email
             (-> request :params :content))))
  (POST "/stripe-checkout" [data] (payment/handle-payment data))
  (route/not-found "Could not find the requested URI"))

(def app
  (-> app-routes
      (wrap-json-response api-defaults)
      (wrap-sentry settings/SENTRY-DSN)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

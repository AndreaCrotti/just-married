(ns just-married.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [environ.core :refer [env]]))

(def DEFAULT-PORT 8080)

(defn home-page
  [request]
  (-> (ring-resp/file-response "index.html" {:root "resources/public"})
      (ring-resp/content-type "text/html")))

(def html-interceptors [(body-params/body-params) http/html-body])
(def routes #{["/" :get (conj html-interceptors `home-page)]})

(defn confirm
  [request]
  (-> (ring-resp/response "hello wolrd")
      (ring-resp/content-type "application/json")))

(def service {:env :prod
              ::http/routes routes
              ::http/allowed-origins ["http://localhost:8080"]
              ;; Root for resource interceptor that is available by default.
              ::http/resource-path "/public"

              ;; this is not really the right way to do this
              ::http/secure-headers {:content-security-policy-settings {:object-src "none"}}
              ::http/type :jetty
              ;; ::http/host "localhost"
              ::http/port (Integer. (or (env :port) DEFAULT-PORT))
              ;; Options to pass to the container (Jetty)
              ::http/container-options {:h2c? true
                                        :h2? false
                                        ;:keystore "test/hp/keystore.jks"
                                        ;:key-password "password"
                                        ;:ssl-port 8443
                                        ;;TODO: would be good to set SSL as well at this point
                                        :ssl? false}})

 

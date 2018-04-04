(ns just-married.system
  (:require [integrant.core :as ig]
            [environ.core :refer [env]]
            [just-married.css :as css]
            [garden.core :as garden]
            [ring.adapter.jetty :as jetty]))

(def ^:private default-port 3000)

(defn- get-port
  []
  (Integer. (or (env :port) default-port)))

(def config
  {:adapter/jetty {:port (get-port)}})

(defmethod ig/init-key :adapter/jetty
  ;; where does the handler come from in this case?
  [_ {:keys [handler] :as opts}]
  (jetty/run-jetty handler {:port (get-port)}))

(defmethod ig/halt-key! :adapter/jetty
  [_ server]
  (.stop server))

(defn- compile-css
  []
  (spit "file.css" (garden/css css/screen)))

(defmethod ig/init-key :garden/compiler
  [_ {:keys [handler] :as opts}]
  )

;; this is how the system is booted up
;; (def system
;;   (ig/init config))

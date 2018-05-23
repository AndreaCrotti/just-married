(ns just-married.core
  (:require [environ.core :refer [env]]
            [just-married.api :refer [app]]
            [ring.adapter.jetty :as jetty]))

(def ^:private default-port 3000)

(defn- get-port
  []
  (Integer. (or (env :port) default-port)))

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))

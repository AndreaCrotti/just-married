(ns just-married.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [environ.core :refer [env]]
            [just-married.api :refer [app]]
            [just-married.api.labels :refer [write-cards]]
            [ring.adapter.jetty :as jetty]))

(def ^:private default-port 3000)

(def cli-options
  [["-n" "--names-file" "File containing names for place cards"]])

(defn- get-port
  []
  (Integer. (or (env :port) default-port)))

(defn -main [& args]
  (let [opts (parse-opts args cli-options)
        names-files (-> opts :options :names-files)]
    (if (some? names-files)
      (write-cards names-files)
      (jetty/run-jetty app {:port (get-port)}))))

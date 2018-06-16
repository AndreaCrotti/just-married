(ns just-married.settings
  (:require [environ.core :refer [env]]))

(def google-analytics-key
  (:google-analytics-key env))

(def ^:private local-pwd "secret")

(def admin-password
  (:admin-password env local-pwd))

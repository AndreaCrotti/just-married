(ns just-married.settings
  (:require [environ.core :refer [env]]))

(def SENDGRID-CONFIG
  {:api-user (:sendgrid-user env)
   :api-key (:sendgrid-password env)})

(def TO-EMAIL
  (:to-email env))

(def FROM-EMAIL
  (:from-email env))

(def SENTRY-DSN
  (:sentry-dsn env))

(defn loaded?
  []
  (not (nil? (:api-key SENDGRID-CONFIG))))

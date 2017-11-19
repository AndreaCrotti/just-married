(ns just-married.settings
  (:require [environ.core :refer [env]]))

(def google-analytics-id "UA-104116805-1")

(def sendgrid-config
  {:api-user (:sendgrid-user env)
   :api-key (:sendgrid-password env)})

(def to-email
  (:to-email env))

(def from-email
  (:from-email env))

(def sentry-dsn
  (:sentry-dsn env))

(def admin-password
  (:admin-password env))

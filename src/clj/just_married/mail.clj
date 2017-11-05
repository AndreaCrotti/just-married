(ns just-married.mail
  (:require [sendgrid.core :as sendgrid]
            [just-married.settings :as settings]))

(defn send-email
  [content]
  ;; TODO: add some proper error handling
  ;; since this raises exceptions for anything
  ;; that is not 200
  (sendgrid/send-email
   settings/sendgrid-config
   {:to settings/to-email
    :from settings/from-email
    :subject "Message for you"
    :text content}))

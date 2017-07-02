(ns just-married.mail
  (:require [sendgrid.core :as sendgrid]
            [just-married.settings :as settings]))

(defn send-email
  [content]
  ;; TODO: add some proper error handling
  ;; since this raises exceptions for anything
  ;; that is not 200
  (let [resp 
        (sendgrid/send-email
         settings/SENDGRID-CONFIG
         {:to settings/TO-EMAIL
          :from settings/FROM-EMAIL
          :subject "Message for you"
          :text content})]
    resp))

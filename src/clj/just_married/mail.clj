(ns just-married.mail
  (:require [sendgrid.core :as sendgrid]
            [clojure.tools.logging :as log]
            [just-married.settings :as settings]))

(def ^:private confirmation-template
  "One of your guests filled in the RVSP form!
  Name: %s
  Email: %s
  Coming: %s
  Other Comment: %s")

(defn gen-subject
  [{:keys [name] :as params}]
  (format "Your friend %s rvspd on andreaenrica.life"
          name))

(defn gen-content
  [{:keys [name email coming comment] :as params}]
  (format confirmation-template
          name
          email
          coming
          comment))

(defn send-email
  [params]
  ;; TODO: add some proper error handling
  ;; since this raises exceptions for anything
  ;; that is not 200

  (let [subject (gen-subject params)
        content (gen-content params)]

    (log/infof "Sending email with subject "
               subject " and content " content
               "to " settings/from-email
               "from " settings/to-email)

    (sendgrid/send-email
     settings/sendgrid-config
     {:to settings/to-email
      :from settings/from-email
      :subject (gen-subject params)
      :text (gen-content params)})))

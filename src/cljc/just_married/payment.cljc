(ns just-married.payment
  (:require
   [stripe.transfer :as transfer]
   [stripe.account :as account]
   [clojure.data.json :as json]))

(defn handle-payment
  [data]
  "Handle a payment call"
  ;; data contains 
  {:status 201
   :body (json/write-str ())})

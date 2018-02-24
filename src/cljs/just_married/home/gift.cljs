(ns just-married.home.gift
  (:require [just-married.home.language :refer [translate]]))

;;TODO: remove if not using stripe after all
(def STRIPE-KEY "pk_test_qtDjM2KBQgYX9vVBhbzrU27F")
(def STRIPE-IMG "https://stripe.com/img/documentation/checkout/marketplace.png")

(def LANGUAGE-TO-CURRENCY
  {:en "gbp"
   :it "euro"})

(def coordinates
  {:gbp {:name "name"
         :iban "long-iban"}

   :eur {:name "name"
         :iban "long-iban-n26"}})

(def gift-dict
  {})

(def ^:private tr
  (partial translate gift-dict))

;; should the wish list url be directly here or injected in some other way?
(defn amazon-wish-list
  "Amazon wish list view"
  []
  [:div {:class "amazon-wish-list"}
   [:h3 "Amazon Wish list"]
   [:a {:href "http://amzn.eu/hzWt6gk"} "Amazon wish list"]])

(defn stripe-form
  [language]
  [:form {:action "/stripe-checkout" :method "POST"}
   [:script
    {:src "https://checkout.stripe.com/checkout.js" :class "stripe-button"
     :data-key STRIPE-KEY
     :data-amount 100
     :data-name "Demo Payment"
     :data-description "Widget"
     :data-image STRIPE-IMG
     :data-locale "auto"
     :data-zip-code "true"
     ;; the gbp should change depending on the language automatically
     :data-currency (get LANGUAGE-TO-CURRENCY language)}
    ]])

(defn gift
  []
  [:div.gift {:id "gift"}
   [:h3 "Gift"]])

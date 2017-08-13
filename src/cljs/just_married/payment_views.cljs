(ns just-married.payment-views)

(def STRIPE-KEY "pk_test_qtDjM2KBQgYX9vVBhbzrU27F")
(def STRIPE-IMG "https://stripe.com/img/documentation/checkout/marketplace.png")

(def LANGUAGE-TO-CURRENCY
  {:english "gbp"
   :italian "euro"})

;; should the wish list url be directly here or injected in some other way?
(defn amazon-wish-list
  ;; does it need to subscribe to anything here?
  ;; maybe extract the user name or something like that?
  []
  "Amazon wish list view"
  (fn []
    [:div {:class "amazon-wish-list"}
     [:a {:href "http://amzn.eu/hzWt6gk"} "Amazon wish list"]]))

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

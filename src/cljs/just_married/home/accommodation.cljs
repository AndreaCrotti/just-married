(ns just-married.home.accommodation
  (:require [just-married.home.language :refer [translate]]
            [goog.string :as gstring]
            [cljs-time.core :as time]
            [clojure.string :refer [upper-case]]
            [just-married.geo-info :refer [place-detail-list]]
            [re-frame.core :refer [dispatch subscribe]]))

;; keeping this here in case we want to show all the information available?
(def prices
  {:single    58.5
   :double    71
   :triple    89
   :quadruple 107})

(def email-hotel "info@hotelvillaimmacolata.it")

(def ^:private accommodation-dict
  {:en {:title     "Accommodation"
        :single    "single"
        :double    "double"
        :triple    "triple"
        :quadruple "quadruple"
        :suggest   "We suggest booking at the Hotel Villa Immacolata."
        :code      "The code to get a 10% discount is \"matrimonio Verrucci\" and the discounted prices (breakfast included) are the following:"
        :distance  "The reception venue is 10 minutes away on foot from the hotel. A bus will be available on the day to take the guests from the hotel to the venue of the ceremony and back."
        :contact   "Contact the hotel at %s for booking and more information."}

   :it {:title     "Dove Dormire"
        :single    "singola"
        :double    "doppia"
        :triple    "tripla"
        :quadruple "quadrupla"
        :suggest   "Suggeriamo di pernottare all'Hotel Villa Immacolata."
        :code      "Il codice per ottenere uno sconto del 10% è \"matrimonio Verrucci\", e i prezzi compresi di sconto sono i seguenti (colazione inclusa):"

        :distance "Il luogo del ricevimento è 10 minuti a piedi dall'hotel. Un pullman sarà disponibile per portare gli ospiti dall'hotel al luogo della cerimonia."
        :contact  "Potete contattare l'hotel alla mail %s per prenotare e per ulteriori informazioni"}})

(def ^:private tr (partial translate accommodation-dict))

(defn prices-table
  []
  [:table.accommodation-prices
   (into [:tr]
         (for [pr (keys prices)]
           [:th (-> pr tr name upper-case)]))

   (into [:tr]
         (for [pr (keys prices)]
           [:td (gstring/format "%s €" (pr prices))]))])

(defn accommodation
  []
  [:div.accommodation.section {:id "accommodation"}
   [:h3 (tr :title)]
   [:div.accommodation__villa-immacolata
    [:div (tr :suggest)]
    [:div (tr :code)]
    [prices-table]
    [:div (tr :distance)]
    [:a {:href (gstring/format "mailto:%s" email-hotel)}
     (gstring/format (tr :contact) email-hotel)]]
   
   [:div.google-map {:id "accommodation-map"}]

   (place-detail-list [:villa :princi])])

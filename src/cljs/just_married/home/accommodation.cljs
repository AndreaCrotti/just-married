(ns just-married.home.accommodation
  (:require [just-married.home.language :refer [translate]]
            [re-frame.core :refer [dispatch subscribe]]))

(def ^:private accommodation-dict
  {:en {:text ""
        :suggest "We suggest booking at the"
        :code "The code to get a 10% discount is simply \"matrimonio Verrucci\""}

   :it {:text ""
        :suggest "Suggeriamo di pernottare all'"
        :code "Il codice per ottenere uno sconto del 10% e' semplicemente \"matrimonio Verrucci\""}})

(def ^:private tr (partial translate accommodation-dict))

(defn accommodation
  []
  [:div.accommodation {:id "accommodation"}
   [:h3 "Accommodation"]
   [:div
    [:p (tr :suggest)
     [:a {:href "https://www.booking.com/hotel/it/parc-villa-immacolata.en-gb.html"}
      " Hotel Villa Immacolata"]]
    [:p (tr :code)]]

   [:div.google-map {:id "accommodation-map"}]])

(ns just-married.home.accommodation
  (:require [just-married.home.language :refer [translate]]
            [re-frame.core :refer [dispatch subscribe]]))

;;TODO: add a couple of links with booking.com searching by area or AirBnb

(def ^:private accommodation-dict
  {:en {:suggest "We suggest booking at the Hotel Villa Immacolata."
        :code "The code to get a 10% discount is simply \"matrimonio Verrucci\""
        :distance "As you can see from the map from Villa Immacolata you can even walk to the party venue, and we will organise a Bus from there to the place of the ceremony."}

   :it {:suggest "Suggeriamo di pernottare all'"
        ;;TODO: verify if the code can be used directly on booking.com or how else
        :code "Il codice per ottenere uno sconto del 10% e' semplicemente \"matrimonio Verrucci\""}})

(def ^:private tr (partial translate accommodation-dict))

(defn accommodation
  []
  [:div.accommodation {:id "accommodation"}
   [:h3 "Accommodation"]
   [:div.accommodation__villa-immacolata
    [:div (tr :suggest)]
    [:div (tr :code)]
    [:div (tr :distance)]]
   
   [:div.google-map {:id "accommodation-map"}]])

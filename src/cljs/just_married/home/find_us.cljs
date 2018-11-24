(ns just-married.home.find-us
  (:require [just-married.home.language :refer [translate]]
            [just-married.geo-info :refer [place-detail-list]]))

(def ^:private find-us-dict
  {:en {:find-us "Find Us"
        :find-us-text "The wedding will be celebrated in the beautiful Abruzzo region, between the cities Chieti and Pescara."}
   :it {:find-us "Come Trovarci"
        :find-us-text ""}})

(def ^:private tr
  (partial translate find-us-dict))

(defn find-us
  []
  [:div.find-us.section {:id "find-us"}
   [:h3 (tr :find-us)]
   [:div.find-us-text (tr :find-us-text)]
   [:div.google-map {:id "map"}]

   (place-detail-list [:palazzo :satakunta])])

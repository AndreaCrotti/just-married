(ns just-married.home.find-us
  (:require [just-married.home.language :refer [translate]]))

(def ^:private find-us-dict
  {:en {:find-us-text "The wedding will be celebrated in the beautiful Abruzzo region, between Chieti and Pescara."}
   :it {:find-us-text ""}})

(defn find-us
  []
  [:div.section {:id "find-us"}
   [:p (translate find-us-dict :find-us-text)]
   [:div {:id "map"}]])

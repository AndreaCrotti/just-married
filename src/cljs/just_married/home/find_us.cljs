(ns just-married.home.find-us
  (:require [just-married.home.language :refer [translate]]))

;; add information about how to actually get there.

(def ^:private find-us-dict
  {:en {:find-us "Find Us"
        :find-us-text "The wedding will be celebrated in the beautiful Abruzzo region, between Chieti and Pescara."}

   :it {:find-us "Come Trovarci"
        :find-us-text ""}})

(def ^:private tr
  (partial translate find-us-dict))

(defn find-us
  []
  [:div.find-us {:id "find-us"}
   [:h3 (tr :find-us)]
   [:p (tr :find-us-text)]
   [:div.google-map {:id "map"}]])

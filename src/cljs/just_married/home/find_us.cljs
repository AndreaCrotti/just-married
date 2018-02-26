(ns just-married.home.find-us
  (:require [just-married.home.language :refer [translate]]))

(def ^:private find-us-dict
  {:en {:find-us "Find Us"}
   :it {:find-us "Come Trovarci"}})

(def ^:private tr
  (partial translate find-us-dict))

(defn find-us
  []
  [:div.find-us {:id "find-us"}
   [:h3 (tr :find-us)]
   [:div.google-map {:id "map"}]])

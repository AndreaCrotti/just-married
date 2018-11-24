(ns just-married.home.timeline
  (:require [just-married.home.language :refer [translate]]))

(def ^:private icons
  {:palazzo "images/rings_small.png"
   :satakunta "images/party_small.png"})

(def ^:private timeline-def
  [["11:30" :palazzo :civil-start]
   ["13:00" :satakunta :restaurant]])

(def ^:private timeline-dict
  {:it {:civil-start "Inizio cerimonia civile"
        :restaurant "Pranzo"
        :timeline "Programma Della Giornata"}})

(def ^:private tr (partial translate timeline-dict))

(defn timeline
  []
  [:div.timeline.section {:id "timeline"}
   [:h3 (tr :timeline)]
   (into [:ul]
         (for [[time place key] timeline-def]
           [:li {:class (place icons)}
            [:img.timeline__icon {:src (place icons)}]
            [:span.timeline__time time]
            [:span.timeline__msg (tr key)]]))])

(ns just-married.home.timeline
  (:require [just-married.home.language :refer [translate]]))

(def menu-link "https://www.dropbox.com/s/uxer2m9krfzwhaq/men%C3%B9.pdf?dl=0")

(def ^:private icons
  {:palazzo "images/rings_small.png"
   :satakunta "images/party_small.png"})

(def ^:private timeline-def
  [["11:30" :palazzo :civil-start]
   ["13:00" :satakunta :restaurant]
   ["17.00" :satakunta :taglio]])

(def ^:private timeline-dict
  {:timeline    "Programma Della Giornata"
   :civil-start "Inizio cerimonia civile"
   :restaurant  [:span "Pranzo " [:a.menu__link
                                  {:href menu-link
                                   :target "_blank"}
                                  "(Vedi il Menu)"]]

   :taglio "Taglio della torta"})

(defn timeline
  []
  [:div.timeline.section {:id "timeline"}
   [:h3 (:timeline timeline-dict)]
   (into [:ul]
         (for [[time place key] timeline-def]
           [:li {:class (place icons)}
            [:img.timeline__icon {:src (place icons)}]
            [:span.timeline__time time]
            [:span.timeline__msg (key timeline-dict)]]))])

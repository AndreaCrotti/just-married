(ns just-married.home.timeline
  (:require [just-married.home.language :refer [translate]]))

(def ^:private timeline-def
  [["11:00" :waiting]
   ["11:15" :civil-start]
   ["12:15" :civil-end]
   ["12:15 - 13:00" :cheers]
   ["13:00" :grand-buffet]
   ["14:00" :lunch-reception]
   ["16:00" :cake-champagne]
   ["16:30" :games-kick-off]
   ["19:00" :evening-reception]
   ["21:00" :music-party]
   ["23:00" :final-cake]])

(def ^:private timeline-dict
  {:en {:waiting "Waiting for the bride aperitif"
        :civil-start "Civil cerimony starts"
        :civil-end "Civil cerimony ends"
        :cheers "Cheers and Greetings to Mr and Mrs Andrea Crotti - Moving to Parco dei Principi"
        :grand-buffet "Grand Buffet"
        :lunch-reception "Lunch Reception"
        :games-kick-off "Games kick-off"
        :evening-reception "Evening Reception"
        :music-party "Music and Evening After Party"
        :cake-champagne "Cake and Champagne"
        :final-cake "Cake and Champagne"}

   :it {:waiting "Waiting for the bride aperitif"
        :civil-start "Civil cerimony starts"
        :civil-end "Civil cerimony ends"
        :cheers "Cheers and Greetings to Mr and Mrs Andrea Crotti - Moving to Parco dei Principi"
        :grand-buffet "Grand Buffet"
        :lunch-reception "Lunch Reception"
        :games-kick-off "Games kick-off"
        :evening-reception "Evening Reception"
        :music-party "Music and Evening After Party"
        :cake-champagne "Cake and Champagne"
        :final-cake "Cake and Champagne"}})

(defn timeline
  []
  [:div.timeline
   [:h3 "Timeline"]
   (into [:ul]
         (for [[time key] timeline-def]
           [:li
            [:span.timeline__time time]
            [:span.timeline__msg (translate timeline-dict key)]]))])

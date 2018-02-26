(ns just-married.home.timeline
  (:require [just-married.home.language :refer [translate]]))

(def ^:private icons
  {:lepri "images/rings_small.png"
   :parco "images/party_small.png"})

(def ^:private timeline-def
  [["11:00" :lepri :waiting]
   ["11:15" :lepri :civil-start]
   ["12:15" :lepri :civil-end]
   ["12:15" :lepri :cheers]
   ["13:00" :parco :grand-buffet]
   ["14:00" :parco :lunch-reception]
   ["16:00" :parco :cake-champagne]
   ["16:30" :parco :games-kick-off]
   ["19:00" :parco :evening-reception]
   ["21:00" :parco :music-party]
   ["23:00" :parco :final-cake]])

(def ^:private timeline-dict
  {:en {:waiting "Waiting for the bride aperitif"
        :timeline "Timeline"
        :civil-start "Civil cerimony starts"
        :civil-end "Civil cerimony ends"
        :cheers "Cheers and Greetings to Mr and Mrs Andrea Crotti"
        :grand-buffet "Grand Buffet"
        :lunch-reception "Lunch Reception"
        :games-kick-off "Games kick-off"
        :evening-reception "Evening Reception"
        :music-party "Music and Evening After Party"
        :cake-champagne "Cake and Champagne"
        :final-cake "Cake and Champagne"}

   :it {:waiting "Aperitivo aspettando la sposa"
        :timeline "Programma Della Giornata"
        :civil-start "Inizio cerimonia civile"
        :civil-end "Fine cerimonia civile"
        :cheers "Saluti agli sposi Crotti - trasferimento al Parco dei Principi"
        :grand-buffet "Grand Buffet"
        :lunch-reception "Pranzo"
        :games-kick-off "Inizio dei giochi"
        :evening-reception "Cena"
        :music-party "Musica e serata"
        :cake-champagne "Torta e Champagne"
        :final-cake "Torta e Champagne"}})

(def ^:private tr (partial translate timeline-dict))

(defn timeline
  []
  [:div.timeline {:id "timeline"}
   [:h3 (tr :timeline)]
   (into [:ul]
         (for [[time place key] timeline-def]
           [:li {:class (place icons)}
            [:img.timeline__icon {:src (place icons)}]
            [:span.timeline__time time]
            [:span.timeline__msg (tr key)]]))])

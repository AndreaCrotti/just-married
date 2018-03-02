(ns just-married.home.countdown
  (:require [reagent.core :as r]
            [just-married.home.settings :refer [FATIDIC-TIME] :as settings]
            [just-married.home.language :refer [translate get-language]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [unparse-duration]]
            [goog.date.duration :as duration]))

(def ^:private countdown-dict
  {:en {:add-to-calendar "Add to Google Calendar"
        :countdown "Countdown"
        :date "27th May, 2018"
        :days "Days"
        :hours "Hours"
        :minutes "Minutes"
        :calendar "https://calendar.google.com/calendar/event?action=TEMPLATE&tmeid=N2NtOHFmYm1tYnIzamY2Zzc4aDFnMWl2NXMgdWQ2bmRiMWhnNWlyMzI5bWZsZzc5cWwxbDRAZw&tmsrc=ud6ndb1hg5ir329mflg79ql1l4%40group.calendar.google.com"}

   :it {:add-to-calendar "Aggiungi al Calendario di Google"
        :countdown "Conto alla rovescia"
        :date "27 Maggio 2018"
        :days "Giorni"
        :hours "Ore"
        :minutes "Minuti"
        :calendar "https://calendar.google.com/calendar/event?action=TEMPLATE&tmeid=M2doaHExNTQwbWM3ZzIyaGt0YnRraXFlc2kgdWQ2bmRiMWhnNWlyMzI5bWZsZzc5cWwxbDRAZw&tmsrc=ud6ndb1hg5ir329mflg79ql1l4%40group.calendar.google.com"}})

(def ^:private tr
  (partial translate countdown-dict))

(defn get-time-left
  []
  (time/interval (time/now) FATIDIC-TIME))

(defonce time-left
  (r/atom (get-time-left)))

(defn extract
  [interval]
  (let [[days hours minutes]
        (re-seq #"\d+" (unparse-duration interval))]
    
    {:days days
     :hours hours
     :minutes minutes}))

(defn reset-left-time
  []
  (reset! time-left (extract (get-time-left))))

;; sample code found https://stackoverflow.com/questions/30280484/making-a-simple-countdown-timer-with-clojure-reagent
(defn countdown-component
  "Generic component for the countdown"
  []
  (r/with-let [timer-fn
               (js/setInterval
                #(swap! time-left reset-left-time) 1000)]

    (let [tr (fn [s] (tr s))]
      [:div.timer {:class ["col-xs" "row"]}
       [:div (str (:days @time-left) " " (tr :days))]
       [:div (str (:hours @time-left) " " (tr :hours))]
       [:div (str (:minutes @time-left) " " (tr :minutes))]])

    (finally (js/clearInterval timer-fn))))

(defn add-to-calendar
  []
  [:a {:target "_blank"
       :href   (tr :calendar)}
   [:img {:src "images/calendar.jpg" :width "80px"}]])


(defn countdown
  []
  [:div.countdown.section {:id "countdown"}
   [:h3 (tr :countdown)]
   [:div.names "Andrea Crotti & Enrica Verrucci"]
   [:span.date (tr :date)]
   [:span.add-to-calendar (add-to-calendar)]
   [:div.countdown__internal
    (countdown-component)]])

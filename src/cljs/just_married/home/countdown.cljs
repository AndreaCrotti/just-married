(ns just-married.home.countdown
  (:require [reagent.core :as r]
            [just-married.home.settings :refer [FATIDIC-TIME]]
            [just-married.home.language :refer [translate]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [unparse-duration]]
            [goog.date.duration :as duration]))

;; sample code found https://stackoverflow.com/questions/30280484/making-a-simple-countdown-timer-with-clojure-reagent

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

(defn countdown-component
  "Generic component for the countdown"
  [language]
  (r/with-let [timer-fn
               (js/setInterval
                #(swap! time-left reset-left-time) 1000)]

    (let [tr (fn [s] (translate language s))]
      [:div.timer {:class ["col-xs" "row"]}
       [:div (str (:days @time-left) " " (tr :days))]
       [:div (str (:hours @time-left) " " (tr :hours))]
       [:div (str (:minutes @time-left) " " (tr :minutes))]])

    (finally (js/clearInterval timer-fn))))

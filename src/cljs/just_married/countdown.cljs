(ns just-married.countdown
  (:require [reagent.core :as r]
            [just-married.settings :refer [FATIDIC-TIME]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [unparse-duration]]
            [goog.date.duration :as duration]))

;; sample code found https://stackoverflow.com/questions/30280484/making-a-simple-countdown-timer-with-clojure-reagent

(defn get-time-left
  []
  (time/interval (time/now) FATIDIC-TIME))

(def time-left
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
  []
  (r/with-let [timer-fn (js/setInterval #(swap! time-left reset-left-time) 1000)]

    [:div.timer
     [:div (str (:days @time-left) " Days")]
     [:div (str (:hours @time-left) " Hours")]
     [:div (str (:minutes @time-left) " Minutes")]]

    (finally (js/clearInterval timer-fn))))

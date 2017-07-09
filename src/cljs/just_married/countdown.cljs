(ns just-married.countdown
  (:require [reagent.core :as r]))

;; sample code found https://stackoverflow.com/questions/30280484/making-a-simple-countdown-timer-with-clojure-reagent

(defn countdown-component []
  (r/with-let [seconds-left (r/atom 60)
               timer-fn     (js/setInterval #(swap! seconds-left dec) 1000)]
    [:div.timer
     [:div "Time Remaining: " (str @seconds-left)]]
    (finally (js/clearInterval timer-fn))))

(defn reset-component [t]
  [:input {:type "button" :value "Reset"
           :on-click #(reset! t 60)}])

(defn countdown-component []
  (let [seconds-left (atom 60)]
    (js/setInterval #(swap! seconds-left dec) 1000)
    (fn []  
      [:div.timer
        [:div "Time Remaining: " (show-time @seconds-left)]
        [reset-component seconds-left]])))

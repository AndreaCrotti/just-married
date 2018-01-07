(ns just-married.home.countdown
  (:require [reagent.core :as r]
            [just-married.home.settings :refer [FATIDIC-TIME] :as settings]
            [just-married.home.language :refer [translate get-language]]
            [cljs-time.core :as time]
            [cljs-time.format :refer [unparse-duration]]
            [goog.date.duration :as duration]))

;; sample code found https://stackoverflow.com/questions/30280484/making-a-simple-countdown-timer-with-clojure-reagent

(def ^:private countdown-dict
  {:en {:add-to-calendar "Add to Calendar"
        :date "27th May, 2018"
        :days "Days"
        :hours "Hours"
        :minutes "Minutes"}

   :it {:add-to-calendar "Aggiungi al Calendario"
        :date "27 Maggio 2018"
        :days "Giorni"
        :hours "Ore"
        :minutes "Minuti"}})

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
  []
  (r/with-let [timer-fn
               (js/setInterval
                #(swap! time-left reset-left-time) 1000)]

    (let [tr (fn [s] (translate countdown-dict s))]
      [:div.timer {:class ["col-xs" "row"]}
       [:div (str (:days @time-left) " " (tr :days))]
       [:div (str (:hours @time-left) " " (tr :hours))]
       [:div (str (:minutes @time-left) " " (tr :minutes))]])

    (finally (js/clearInterval timer-fn))))

(defn add-to-calendar
  []
  [:div.add-to-calendar
   [:a {:target "_blank"
        :href (get settings/WEDDING-DAY (get-language))}

    ;; actually use current language
    (translate countdown-dict :add-to-calendar)
    [:img {:src settings/GOOGLE-CALENDAR-IMG}]]])

;;TODO: evaluate using a macro to avoid redefining the same king
;;of function every time

(defn countdown
  []
  [:div.countdown.section {:id "countdown"}
   [:div.names "Andrea Crotti & Enrica Verrucci"]
   [:div.find-us
    [:a {:href "#find-us"} (-> settings/PLACES :wedding :name)]]

   [:div.date (translate countdown-dict :date)]
   [:div.countdown__internal
    (countdown-component)]

   (add-to-calendar)])

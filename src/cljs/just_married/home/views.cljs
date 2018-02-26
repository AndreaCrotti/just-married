(ns just-married.home.views
  (:require [just-married.shared :refer [sections]]
            [just-married.home.navbar :refer [navbar]]
            [just-married.home.countdown :refer [countdown]]
            [just-married.home.timeline :refer [timeline]]
            [just-married.home.find-us :refer [find-us]]
            [just-married.home.accommodation :refer [accommodation]]
            [just-married.home.gift :refer [gift]]))

(def sections-roots
  {:countdown countdown
   ;; :story story
   :find-us find-us
   :timeline timeline
   ;; :rvsp rvsp
   :accommodation accommodation
   :gift gift})

(def sections-filtered
  (select-keys sections-roots
               (set sections)))

(defn main-panel
  []
  ;;TODO: there could be a better way to do this
  (into [:g]
        (cons
         [navbar (keys sections-filtered)]
         (vector (into [:div.container]
                       (map vector (vals sections-filtered)))))))

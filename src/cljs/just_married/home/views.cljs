(ns just-married.home.views
  (:require
   [just-married.home.settings :as settings]
   [just-married.home.countdown :refer [countdown]]
   [just-married.home.timeline :refer [timeline]]
   [just-married.home.find-us :refer [find-us]]
   [just-married.home.navbar :refer [navbar]]
   [just-married.home.accommodation :refer [accommodation]]
   [just-married.home.gift :refer [gift]]
   #_[just-married.home.rvsp :refer [rvsp]]
   #_[just-married.home.story :refer [story]]))


;; should dispatch the right language also here of course

(defn gifts
  []
  [:div.section {:id "gifts"}
   [:div {:id "amazon-wish-list"}
    [:a {:href settings/AMAZON-WISH-LIST} "Amazon wish list"]]])

(def SECTIONS
  {:countdown countdown
   ;; :story story
   :find-us find-us
   :timeline timeline
   ;; :rvsp rvsp
   ;; :gifts gifts
   :accommodation accommodation
   ;; :contacts contacts
   })


(defn main-panel
  []
  ;;TODO: there could be a better way to do this
  (into [:g]
        (cons
         [navbar (keys SECTIONS)]
         (vector (into [:div.container]
                       (map vector (vals SECTIONS)))))))

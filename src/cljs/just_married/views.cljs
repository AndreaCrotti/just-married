(ns just-married.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [just-married.language :refer [make-lang AVAILABLE-LANGUAGES]]
   [just-married.payment-views :as payment-views]))

(defn header
  ;; do we need the language available all over the place??
  []
  ;; the actual spacing and similar stuff can be done
  ;; with CSS directly
  [:div {:id "header"}
   [:a {:href "#story"} "Our Story"]
   [:a {:href "#find-us"} "Find us"]
   [:a {:href "#gift"} "Donate"]
   [:a {:href "#rvsp"} "RVSP"]
   [:a {:href "#share"} "Share Your Memories"]
   [:a {:href "#get-there"} "Directions"]
   [:a {:href "#accommodation"} "Accomodation"]
   [:a {:href "#contacts"}]])

(defn lang-selection
  "Define all the possible languages as sequences of clickable images"
  []
  (let [current-language (subscribe [:current-language])]
    (fn []
      (into
       [:div {:class "language-group"}]
       (for [lang AVAILABLE-LANGUAGES]
         (make-lang lang @current-language))))))

;; devtools does not seem to be set up correctly
;; since it doesn't find hints.js for example
;; (log "hello")

(defn main-panel
  []
  (fn []
    [:g
     [header]
     [lang-selection]
     [payment-views/amazon-wish-list]]
    ))

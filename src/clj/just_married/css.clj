(ns just-married.css
  (:require [garden.def :refer [defstyles]]))

(def COLOR-PALLETTE
  {:amaranth "#E52B50"
   :dark-red "#8B0000"
   :blue "#4444FF"})

(def FONT-FAMILIES
  {:open-sans "'Open Sans', sans-serif"})

;; each style defines a new file, could simply also generate
;; many different files ideally
(defstyles screen
  ;; various language settings
  [:.language-group {:padding-right "20px"}]
  [:.language.selected {:padding-right "20px"}]
  [:.language.not-selected {:padding-right "20px"}]

  ;; header settings
  [:#header {:background-color (:amaranth COLOR-PALLETTE)
             :font-color (:blue COLOR-PALLETTE)}]

  [:#header [:a {:padding-right "20px"
                 :font-weight "bold"
                 :font-family (:open-sans FONT-FAMILIES)}]]
  
  ;; this should be an id instead of this
  [:#submit-button {:background-color "red" :font-weight "bold"}]

  ;; boxing sections properly
  [:.section {:border-width "2px"
              :border-style "solid"}]
)

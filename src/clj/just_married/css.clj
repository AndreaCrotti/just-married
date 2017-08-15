(ns just-married.css
  (:require [garden.def :refer [defstyles defcssfn]]
            [garden.core :refer [css]]))

;; for some reason this is not already available but at least it's
;; very easy to define
(defcssfn url)

(def COLOR-PALLETTE
  {:amaranth "#E52B50"
   :dark-red "#8B0000"
   :blue "#4444FF"
   :marsala "#955251"
   :royal-blue "#4169e1"
   :deep-blue "#00bfff"
   :electric-blue "#2C75FF"
   :deep-electric-blue "#035096"})

(def FONT-FAMILIES
  {:open-sans "'Open Sans', sans-serif"
   :alex-brush "'Alex Brush', cursive"})

(def FONT-STYLES
  {:big
   {:font-weight "bolder"
    :font-family (:open-sans FONT-FAMILIES)}})

;; define various font styles that can be used here

;; the base path in this case refers to where the css will be
;; generated into
(def IMAGES
  {:bw-cats "../images/cats_heart.jpg"
   :small-cats "../images/small_cats.jpg"})

;; each style defines a new file, could simply also generate
;; many different files ideally
(defstyles screen
  ;;TODO: make it actually still look nice though
  [:a {:color "inherit"
       :text-decoration "none"}]

  [:a:hover {:text-decoration "none"
             :color "inherit"}]

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

  ;; boxing sections properly
  [:.section {:border-width "2px"
              ;;:border-style "solid"
              }]

  [:app {:background-color (:marsala COLOR-PALLETTE)}]
  ;; maybe the image containing the rest should actually be an element
  ;; by itself?
  [:#story {:background-image
            [(url (format "'%s'" (:bw-cats IMAGES)))]
            :background-repeat "no-repeat"
            :width "500px"
            :height "400px"
            :text-align "center"
            :font-weight "bold"
            :font-family (:alex-brush FONT-FAMILIES)
            :font-size "x-large"
            :color (:marsala COLOR-PALLETTE)}]

  ;; countdown settings
  [:.timer {;:background-color (:marsala COLOR-PALLETTE)
            :font-weight "bolder"
            :color (:deep-blue COLOR-PALLETTE)}]
  
  ;; find us settings
  [:#map {:width "400px" :height "400px"}]
)

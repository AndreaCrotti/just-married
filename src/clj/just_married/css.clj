(ns just-married.css
  (:require [garden.def :refer [defstyles defcssfn]]
            [garden.core :refer [css]]))

(def COLOR-PALLETTE
  {:amaranth "#E52B50"
   :white "#FFFFFF"
   ;; :light-background "#dcdcdc"
   :light-background "#f9eef0"
   :dark-red "#8B0000"
   :blue "#4444FF"
   :gold "#FFd700"
   :marsala "#955251"
   :royal-blue "#4169e1"
   :deep-blue "#00bfff"
   :electric-blue "#2C75FF"
   :deep-electric-blue "#035096"
   :quote-background "#f9f9f9"
   :quote-border-left "#ccc"
   :map-background "#fff"})

(def FONT-FAMILIES
  {:open-sans "'Open Sans', sans-serif"
   :alex-brush "'Alex Brush', cursive"
   :rancho "'Rancho', cursive"})

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

(def common
  [[:a {:text-decoration "none"}]
   [:a:hover {:text-decoration "none"}]])

(def main-page
  [[:.container {:display "grid"
                 :grid-gap "5px"
                 :grid-template-columns "300px auto"
                 :grid-template-rows "auto auto"}]

   [:.countdown {:grid-column 1
                 :grid-row 1
                 :color (:marsala COLOR-PALLETTE)
                 :font-weight "bold"
                 :font-family (:alex-brush FONT-FAMILIES)
                 :font-size "x-large"}]

   [:.story {:grid-column 2
             :grid-row 1}]

   [:.find-us {:grid-column "span 1 / 2"
               :grid-row 2}]

   [:blockquote:before {:color (:quote-border-left COLOR-PALLETTE)
                        :content "open-quote"
                        :font-size "3em"
                        :line-height ".1em"
                        :margin-right ".25em"
                        :vertical-align "-0.4em"}]

   [:blockquote {:background (:quote-background COLOR-PALLETTE)
                 :padding "0.5em 10px"
                 :font-size "1em"
                 :font-family (:open-sans FONT-FAMILIES)
                 :border-left (format "10px solid %s" (:quote-border-left COLOR-PALLETTE))
                 :quotes "\201C\201D\2018\2019"}]

   [:blockquote:after {:color (:quote-border-left COLOR-PALLETTE)
                       :content "close-quote"
                       :font-size "2em"
                       :line-height ".1em"
                       :margin-right ".25em"
                       :vertical-align "-.2em"
                       :box-shadow "0 0 20px #999c"}]

   [:app {:background-color (:marsala COLOR-PALLETTE)}]
   ;; maybe the image containing the rest should actually be an element
   ;; by itself?
   [:.countdown__internal {:background (:quote-background COLOR-PALLETTE)
                           :background-repeat "no-repeat"
                           :text-align "center"}]

   ;; countdown settings
   [:.timer {;:background-color (:marsala COLOR-PALLETTE)
             :font-weight "bolder"
             :color (:deep-blue COLOR-PALLETTE)}]

   ;; find us settings
   [:#map {:width "500px"
           :height "500px"
           :padding "15px"
           :box-shadow "10px 10px 20px #999"
           :border-radius "10px"}]

   [:.navbar__container {:background-color (:dark-red COLOR-PALLETTE)
                         :font-weight "bolder"
                         :display "grid"
                         :font-size "1.2em"
                         :grid-template-columns "repeat(5, 1fr)"
                         :grid-auto-rows "auto"
                         :grid-gap "1em"
                         :align-items "center"
                         :text-align "center"
                         :text-decoration "none"
                         :color (:gold COLOR-PALLETTE)
                         :margin-bottom "50px"
                         :position "sticky"
                         :top "0"}

    ;; FIXME: should be less generic here
    [:a {:color (:white COLOR-PALLETTE)}]

    [:a:hover {:color (:gold COLOR-PALLETTE)
               :border-left "1px solid white"
               :border-right "1px solid white"}]]])

(def enter-page
  [[:.initial__root {:display "grid"
                     :width "100%"
                     :height "auto"
                     :bottom "0px"
                     :top "0px"
                     :left 0
                     :justify-items "center"
                     :position "absolute"
                     :grid-template-columns "auto auto"
                     :grid-template-rows "auto auto auto"
                     :background-color (:dark-red COLOR-PALLETTE)}]

   [:.monogram__container {:grid-row "1"
                           :grid-column "span 2"}]

   [:.date__container {:grid-column "span 2"
                       :grid-row "2"
                       :font-weight "bolder"
                       :font-size "4em"
                       :text-shadow "2px 2px 2px"
                       :font-family (:open-sans FONT-FAMILIES)
                       :color (:gold COLOR-PALLETTE)}]

   [:.language__detector__english {:grid-column "1"
                                   :grid-row "3"}]

   [:.language__detector__italian {:grid-column "2"
                                   :grid-row "3"}]])

(defstyles screen
  ;; could maybe even split creating multiple CSS files?
  (into []
        (concat common
                main-page
                enter-page)))

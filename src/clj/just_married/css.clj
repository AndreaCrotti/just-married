(ns just-married.css
  (:require [garden.def :refer [defstyles defcssfn]]
            [garden.stylesheet :refer [at-media]]
            [garden.core :refer [css]]))

(def ^:private map-width "500px")
(def ^:private max-width-mobile "480px")

(def COLOR-PALLETTE
  {:amaranth "#E52B50"
   :white "#FFFFFF"
   :navy "#0e0e4c"
   ;; :light-background "#dcdcdc"
   :light-background "#f9eef0"
   :dark-red "#8B0000"
   :blue "#4444FF"
   :gold "#FFd700"
   :golden-yellow "#FFDF00"
   :metallic-gold "#D4AF37"
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

(def ^:private common-grid-options
  {:display "grid"
   :grid-gap "5px"})

(def ^:private grid-config
  {:desktop (merge common-grid-options {:grid-template-columns "550px auto"
                                        :grid-template-rows "auto auto"})

   :mobile (merge common-grid-options {:grid-template-columns "auto"
                                       :grid-template-rows "auto auto auto auto"})})

(def ^:private navbar-grid-config
  {:desktop {:background-color (:dark-red COLOR-PALLETTE)
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
             :top "0"
             ;; to avoid making it go behind the map
             :z-index 100}

   :mobile {:background-color (:dark-red COLOR-PALLETTE)
            :font-weight "bolder"
            :display "grid"
            :font-size "2em"
            :grid-template-columns "auto"
            :grid-template-rows "repeat(4, 1fr)"
            :grid-auto-rows "auto"
            :grid-gap "1em"
            :align-items "left"
            :text-align "left"
            :text-decoration "none"
            :color (:gold COLOR-PALLETTE)
            :margin-bottom "50px"
            :padding-top "10px"
            :padding-left "10px"
            :position "sticky"
            :top "0"
            ;; to avoid making it go behind the map
            :z-index 100}})

(def common
  [[:a {:text-decoration "none"}]
   [:a:hover {:text-decoration "none"}]])

(def main-page
  [[:.container (:desktop grid-config)]

   [:h3 {:color "white"
         :background-color (:dark-red COLOR-PALLETTE)}]
   [:.countdown {:grid-column "span 2"
                 :grid-row 1
                 :color (:marsala COLOR-PALLETTE)
                 :font-weight "bold"
                 ;; might be nice to shrink this in theory??
                 ;; :width "50%"
                 :font-family (:alex-brush FONT-FAMILIES)
                 :font-size "x-large"}]

   ;; [:.story {:grid-column 2
   ;;           :grid-row 1}]

   [:.find-us {:grid-column 1
               :grid-row 2}]

   [:.timeline {:grid-column 2
                :grid-row 2}]

   [:.timeline__time {:padding-right "10px"}]

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
   [:#map {:width map-width
           :height map-width
           :padding "15px"
           :box-shadow "10px 10px 20px #999"
           :border-radius "10px"}]

   [:.navbar__container (:desktop navbar-grid-config)

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
                       :font-size "5em"
                       :text-shadow "2px 2px 2px"
                       :font-family (:alex-brush FONT-FAMILIES)
                       :color (:navy COLOR-PALLETTE)}]

   [:.flag {:width "100px"}]

   [:.language__detector__english {:grid-column "1"
                                   :grid-row "3"
                                   :padding-left "150px"}]

   [:.monogram {:width "400px"}]
   [:.language__detector__italian {:grid-column "2"
                                   :grid-row "3"
                                   :padding-right "150px"}]])

(def responsive
  [[(at-media {:screen true
               :max-device-width max-width-mobile}

              [:.navbar__container (:mobile navbar-grid-config)]
              [:.container (:mobile grid-config)]
              [:.timeline {:grid-column 1
                           :grid-row 3}])]])

(defstyles screen
  ;; could maybe even split creating multiple CSS files?
  (into []
        (concat common
                main-page
                enter-page
                responsive)))

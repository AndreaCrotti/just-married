(ns just-married.css
  (:require [garden.def :refer [defstyles defcssfn]]
            [garden.stylesheet :refer [at-media]]
            [just-married.shared :refer [sections]]
            #_[garden.core :refer [css]]))

(def ^:private map-width "500px")
(def ^:private max-width-mobile "480px")
(def num-sections (-> sections count))
(def menu-size (format "repeat(%d, 1fr)" (inc num-sections)))

(defn repeat-word
  [word times]
  (clojure.string/join " "
                       (take times (cycle [word]))))

(def COLOR-PALLETTE
  {:white "#FFFFFF"
   :navy "#0e0e4c"
   :light-grey "#DDDDDD"
   :dark-red "#8B0000"
   :gold "#FFd700"
   :marsala "#955251"
   :quote-background "#f9f9f9"
   :quote-border-left "#ccc"})

(def FONT-FAMILIES
  {:open-sans "'Open Sans', sans-serif"
   :alex-brush "'Alex Brush', cursive"
   :rancho "'Rancho', cursive"})

(def FONT-STYLES
  {:big
   {:font-weight "bolder"
    :font-family (:open-sans FONT-FAMILIES)}})

(def ^:private common-grid-options
  {:display               "grid"
   :grid-gap              "5px"
   :grid-template-columns "auto"
   :grid-template-rows    (repeat-word "auto" num-sections)
   :padding-left          "40px"
   :width                 "600px"})

(def ^:private navbar-grid-config
  {:desktop {:background-color (:dark-red COLOR-PALLETTE)
             :font-weight "bolder"
             :display "grid"
             :font-size "1.5em"
             :grid-template-columns menu-size
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
            :grid-template-rows menu-size
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
  [[:.container common-grid-options]
   [:h3 {:color (:dark-red COLOR-PALLETTE)}]
   [:body {:color (:navy COLOR-PALLETTE)
           :font-family (:alex-brush FONT-FAMILIES)}]
   [:.countdown {:grid-column 1
                 :grid-row 1
                 ;; :color (:marsala COLOR-PALLETTE)
                                        ;:font-weight "bold"
                 ;; might be nice to shrink this in theory??
                 ;; :width "50%"
                 ;;:font-family (:alex-brush FONT-FAMILIES)
                 :font-size "x-large"}]

   [:.button__add-to-calendar {:background-color (:dark-red COLOR-PALLETTE)
                               :color (:white COLOR-PALLETTE)}]

   [:.timeline__time {:padding-right "10px"
                      :font-weight "bolder"}]

   [:.timeline__icon {:width "22px"
                      :padding-right "5px"}]

   [:.bank_table {:font-family (:open-sans FONT-FAMILIES)}]

   [:th {:padding "4px"}]
   [:td {:padding "5px"
         :text-align "left"
         :border (format "0.8px solid %s" (:navy COLOR-PALLETTE))}]

   [:.accommodation__villa-immacolata {:padding-bottom "20px"}]

   [:ul {:list-style "none"}]

   [:.rvsp__form {:display "grid"
                  :grid-gap "10px"
                  :grid-template-columns "auto auto"
                  :grid-template-rows "auto auto auto auto auto auto"}]

   [:.rvsp__confirm {:width "200px"
                     :border-radius "4px"
                     :padding "10px"
                     :font-size "1.3em"
                     :text-decoration "none"
                     :display "inline-block"
                     :border "none"
                     :cursor "hand"
                     :color (:white COLOR-PALLETTE)}]

   [:.rvsp__confirm:hover {:font-size "1.5em"}]

   [:.rvsp__coming {:background-color (:dark-red COLOR-PALLETTE)}]

   [:.rvsp__not_coming {:background-color (:navy COLOR-PALLETTE)}]

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
                       :box-shadow (format "0 0 20px %s" (:dark-red COLOR-PALLETTE))}]

   [:app {:background-color (:marsala COLOR-PALLETTE)}]
   ;; maybe the image containing the rest should actually be an element
   ;; by itself?
   [:.countdown__internal {:text-align "center"
                           :width "500px"}]

   ;; countdown settings
   [:.timer {;:background-color (:marsala COLOR-PALLETTE)
             :border-width "1px"
             :border-style "solid"
             :font-size "2em"
             :font-family (:alex-brush FONT-FAMILIES)
             :border-color (:dark-red COLOR-PALLETTE)}]

   ;; find us settings
   [:.google-map {:width map-width
                  :height map-width
                  :padding "15px"
                  :box-shadow (format "10px 10px 20px %s" (:light-grey COLOR-PALLETTE))
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
                           :grid-column "span 2"
                           :margin-top "100px"}]

   [:.date__container {:grid-column "span 2"
                       :grid-row "2"
                       :font-size "5em"
                       :text-shadow "2px 2px 2px"
                       :font-family (:alex-brush FONT-FAMILIES)
                       :color (:white COLOR-PALLETTE)}]

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
              [:.container common-grid-options]
              ;; should I simply change the layout
              ;; entirely moving with everything on the same column??
              [:.timeline {:grid-column 1
                           :grid-row 3}])]])

(defstyles screen
  ;; could maybe even split creating multiple CSS files?
  (into []
        (concat common
                main-page
                enter-page
                responsive)))

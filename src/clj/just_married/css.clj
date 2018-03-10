(ns just-married.css
  (:require [garden.def :refer [defstyles defcssfn]]
            [garden.core :refer [css]]
            [garden.stylesheet :refer [at-media]]
            [just-married.shared :refer [sections]]))

(def ^:private map-height "500px")
(def ^:private max-width-mobile "480px")

;; dynamically get the grid size from the actual sections declared
(def num-sections (-> sections count))
(def menu-size (format "repeat(%d, 1fr)" (inc num-sections)))
(defn repeat-word
  [word times]
  (clojure.string/join " "
                       (take times (cycle [word]))))

(def color-pallette
  {:white             "#FFFFFF"
   :navy              "#0e0e4c"
   :light-grey        "#DDDDDD"
   :dark-red          "#8B0000"
   :gold              "#FFd700"
   :marsala           "#955251"
   :quote-background  "#f9f9f9"
   :quote-border-left "#ccc"})

(def font-families
  {:open-sans  "'Open Sans', sans-serif"
   :alex-brush "'Alex Brush', cursive"
   :rancho     "'Rancho', cursive"
   :satisfy    "'Satisfy', cursive"})

(def ^:private body-grid-config
  {:display               "grid"
   :grid-gap              "5px"
   :grid-template-columns "auto 80% auto"
   :grid-template-rows    (repeat-word "auto" num-sections)
   :justify-items         "left"
   :justify-content       "center"
   :width                 "90%"})

(def ^:private navbar-grid-config
  {:background-color      (:dark-red color-pallette)
   :font-weight           "bolder"
   :display               "grid"
   :text-align            "center"
   :text-decoration       "none"
   ;; to avoid making it go behind the map
})

(def ^:private navbar-grid-config
  ;; simply invert rows and columns while using the same grid
  ;; otherwise
  {:desktop (merge navbar-grid-config
                   {:grid-template-columns menu-size
                    :position              "sticky"
                    :top                   0
                    :grid-template-rows    "auto"
                    :grid-gap              "1em"
                    :z-index               100})

   :mobile (merge navbar-grid-config
                  {:grid-template-columns "auto"
                   :grid-template-rows    menu-size
                   :font-size             "1.9em"
                   :grid-gap              "5px"
                   :position              "relative"
                   :z-index               0})})

(def common
  [[:a {:text-decoration "none"}]
   [:a:hover {:text-decoration "none"}]])

(def main-page
  [[:.container body-grid-config]
   [:.section {:padding-top "40px"}]
   [:.find-us {:grid-column "2"}]
   [:.countdown {:grid-column "2"}]

   [:.timeline {:grid-column "2"}]
   [:.gift {:grid-column "2"}]
   [:.accommodation {:grid-column "2"}]
   [:.rvsp {:grid-column "2"}]

   [:.accommodation-prices {:padding-bottom "30px"}]

   [:h3 {:color (:dark-red color-pallette)}]
   [:body {:color       (:navy color-pallette)
           :font-family (:satisfy font-families)
           :font-size   "1.4em"}]

   [:.names {:font-size "1.4em"
             :padding-bottom "10px"}]
   [:.date {:font-size "1.4em"}]
   [:.countdown__internal {:text-align "center"
                           :width      "500px"}]
   [:.add_to_calendar_button {:padding-left "30px"}]

   [:.timer {:font-size    "1.5em"
             :border-color (:dark-red color-pallette)
             :margin-top   "10px"}]

   [:.button__add-to-calendar {:background-color (:dark-red color-pallette)
                               :color            (:white color-pallette)}]

   [:.timeline__time {:padding-right "10px"
                      :font-weight   "bolder"}]

   [:.timeline__icon {:width         "22px"
                      :padding-right "5px"}]

   [:.bank_table {:font-family (:open-sans font-families)}]
   [:.accommodation-prices {:font-family (:open-sans font-families)
                            :padding-top "10px"
                            :padding-left "30px"}]

   [:th {:padding "4px"}]
   [:td {:padding    "5px"
         :text-align "left"
         :border     (format "0.8px solid %s" (:navy color-pallette))}]

   [:.accommodation__villa-immacolata {:padding-bottom "20px"}]

   [:ul {:list-style "none"}]

   [:.rvsp__form {:display               "grid"
                  :grid-gap              "10px"
                  :grid-template-columns "auto auto"
                  :grid-template-rows    "auto auto auto auto auto auto"}]

   [:.rvsp__confirm {:width           "200px"
                     :border-radius   "4px"
                     :padding         "10px"
                     :font-size       "1.3em"
                     :text-decoration "none"
                     :display         "inline-block"
                     :border          "none"
                     :cursor          "hand"
                     :color           (:white color-pallette)}]

   [:.rvsp__confirm:hover {:font-size "1.5em"}]

   [:.rvsp__coming {:background-color (:dark-red color-pallette)}]

   [:.rvsp__not_coming {:background-color (:navy color-pallette)}]

   [:blockquote:before {:color          (:quote-border-left color-pallette)
                        :content        "open-quote"
                        :font-size      "3em"
                        :line-height    ".1em"
                        :margin-right   ".25em"
                        :vertical-align "-0.4em"}]

   [:blockquote {:background  (:quote-background color-pallette)
                 :padding     "0.5em 10px"
                 :font-size   "1em"
                 :font-family (:open-sans font-families)
                 :border-left (format "10px solid %s" (:quote-border-left color-pallette))
                 :quotes      "\201C\201D\2018\2019"}]

   [:blockquote:after {:color          (:quote-border-left color-pallette)
                       :content        "close-quote"
                       :font-size      "2em"
                       :line-height    ".1em"
                       :margin-right   ".25em"
                       :vertical-align "-.2em"
                       :box-shadow     (format "0 0 20px %s" (:dark-red color-pallette))}]

   [:app {:background-color (:marsala color-pallette)}]

   ;; find us settings
   [:.google-map {:width         "90%"
                  :height        map-height
                  :padding       "15px"
                  :box-shadow    (format "10px 10px 20px %s" (:light-grey color-pallette))
                  :border-radius "10px"}]

   [:.find-us-text {:padding-bottom "30px"}]

   [:.navbar__container (:desktop navbar-grid-config)

    ;; FIXME: should be less generic here
    [:a {:color (:white color-pallette)}]

    [:a:hover {:color        (:gold color-pallette)
               :border-left  "1px solid white"
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
                     :background-color (:dark-red color-pallette)}]

   [:.monogram__container {:grid-row "1"
                           :grid-column "span 2"
                           :margin-top "100px"}]

   [:.date__container {:grid-column "span 2"
                       :grid-row "2"
                       :font-size "5em"
                       :text-shadow "2px 2px 2px"
                       :font-family (:alex-brush font-families)
                       :color (:white color-pallette)}]

   [:.flag {:width "100px"}]

   [:.language__detector__english {:grid-column "1"
                                   :grid-row "3"
                                   :padding-left "150px"}]

   [:.monogram {:width "400px"}]
   [:.language__detector__italian {:grid-column "2"
                                   :grid-row "3"
                                   :padding-right "150px"}]])

(def responsive
  [[(at-media {:screen           true
               :max-device-width max-width-mobile}

              [:.section {:padding-top "5px"}]
              [:.navbar__container (:mobile navbar-grid-config)]
              [:.container body-grid-config])]])

(defstyles screen
  ;; could maybe even split creating multiple CSS files?
  (into []
        (concat common
                main-page
                enter-page
                responsive)))

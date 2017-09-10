(ns just-married.language
  (:require [tongue.core :as tongue]
            [re-frame.core :as re-frame :refer [dispatch subscribe]]))

(def AVAILABLE-LANGUAGES
  "All the available languages"
  #{:en :it})

(defn- set-lang [lang]
  (dispatch [:set-language lang]))

(def flag-files
  (zipmap AVAILABLE-LANGUAGES
          (map #(subs (str % ".png") 1) AVAILABLE-LANGUAGES)))

(defn make-lang [lang current-language]
  (let [selected (= lang current-language)
        png-file (lang flag-files)
        props {:type "image" :src png-file :key lang}
        full-props (if selected
                     ;; the default language does not
                     ;; get selected at the moment
                     (assoc props :class "language.selected" )
                     (assoc props
                            :class "language.not-selected"
                            :on-click #(dispatch [:set-language lang])))]

    [:input full-props]))

(defn lang-selection
  "Define all the possible languages as sequences of clickable images"
  [current-language]
  [:div {:id "language-selection"}
   (for [lang AVAILABLE-LANGUAGES]
     (make-lang lang current-language))])

(def dicts
  "List of all the words/sentences that need localization"
  {:en {:story "Our Story"
        :find-us "Find Us"
        :rvsp "RVSP"
        :accomodation "Accommodation"
        :contacts "Contacts"
        :add-to-calendar "Add to Calendar"
        :date "27th May, 2018"
        :days "Days"
        :hours "Hours"
        :minutes "Minutes"}

   :it {:story "La Nostra Storia"
        :find-us "Trovaci"
        :rvsp "RVSP"
        :accomodation "Dove Dormire"
        :contacts "Contatti"
        :add-to-calendar "Aggiungi al Calendario"
        :date "27 Maggio 2018"
        :days "Giorni"
        :hours "Ore"
        :minutes "Minuti"}})

(def translate
  (tongue/build-translate dicts))

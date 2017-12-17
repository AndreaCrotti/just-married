(ns just-married.home.language
  (:require [tongue.core :as tongue]
            [re-frame.core :as re-frame :refer [dispatch subscribe]]))

(def available-languages
  "All the available languages"
  #{:en :it})

(defn- set-lang [lang]
  (dispatch [:set-language lang]))

(def lang->flag-file
  (zipmap available-languages
          (map #(subs (str % ".png") 1) available-languages)))

(defn language-selected->props
  [lang]
  {true {:class "language.selected"}
   false {:class "language.not-selected"
          :on-click #(dispatch [:set-language lang])}})

(defn make-lang [lang current-language]
  (let [selected (= lang current-language)
        png-file (lang lang->flag-file)
        props {:type "image" :src png-file :key lang}
        selected-props (get (language-selected->props lang) selected)
        full-props (merge props selected-props)]

    [:input full-props]))

(defn lang-selection
  "Define all the possible languages as sequences of clickable images"
  [current-language]
  (for [lang available-languages]
    (make-lang lang current-language)))

(def dicts
  "List of all the words/sentences that need localization"
  {:en {:story "Our Story"
        :find-us "Find Us"
        :find-us-text "The wedding will be done in the beautiful Abruzzo region, between Chieti and Pescara."
        :countdown "Countdown"
        :accomodation "Accommodation"
        :contacts "Contacts"
        :add-to-calendar "Add to Calendar"
        :date "27th May, 2018"
        :days "Days"
        :hours "Hours"
        :minutes "Minutes"
        :read-more "Read More"
        :read-less "Read Less"}

   :it {:story "La Nostra Storia"
        :find-us "Trovaci"
        :countdown "Conto Alla Rovescia"
        :accomodation "Dove Dormire"
        :contacts "Contatti"
        :add-to-calendar "Aggiungi al Calendario"
        :date "27 Maggio 2018"
        :days "Giorni"
        :hours "Ore"
        :minutes "Minuti"
        :read-more "Leggi di piu'"
        :read-less "Leggi di meno"}})

(def translate
  (tongue/build-translate dicts))

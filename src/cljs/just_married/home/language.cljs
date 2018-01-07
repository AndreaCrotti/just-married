(ns just-married.home.language
  (:require [tongue.core :as tongue]
            [cemerick.url :refer [url]]))

(def available-languages
  "All the available languages"
  #{:en :it})

(defn get-language
  []
  (-> js/window
      .-location
      .-href
      url
      :query
      (get "language" "en")
      keyword))

(def dicts
  "List of all the words/sentences that need localization"
  {:en {:home "Home"
        :timeline "Timeline"
        :story "Our Story"
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
        :read-less "Read Less"

        ;; timeline translation
        :waiting "Waiting for the bride Aperitif"
        }

   :it {:home "Home"
        :timeline "Programma"
        :story "La Nostra Storia"
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

(defn translate
  [dict key]
  ((tongue/build-translate dict) (get-language) key))

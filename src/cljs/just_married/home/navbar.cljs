(ns just-married.home.navbar
  (:require [just-married.home.language :refer [translate get-language]]))

(def ^:private navbar-dict
  {:en {:timeline "Timeline"
        :rvsp "RVSP"
        :story "Our Story"
        :find-us "Find Us"
        :countdown "Countdown"
        :accommodation "Accommodation"
        :contacts "Contacts"
        :gift "Wedding Registry"}

   :it {:timeline "Programma"
        :story "La Nostra Storia"
        :find-us "Trovaci"
        :rvsp "RVSP"
        :countdown "Conto Alla Rovescia"
        :accommodation "Dove Dormire"
        :contacts "Contatti"
        :gift "Lista Nozze"}})

(defn navbar
  "Generate the full navbar from the sections"
  [sections]
  (into [:div.navbar__container]
        (for [sec sections]
          (let [href (str "#" (name sec))]
            [:div.navbar__link
             [:a {:href href}
              (translate navbar-dict sec)]]))))


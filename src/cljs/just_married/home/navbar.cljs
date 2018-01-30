(ns just-married.home.navbar
  (:require [just-married.home.language :refer [translate get-language]]))

(def ^:private navbar-dict
  {:en {:timeline "Timeline"
        :story "Our Story"
        :find-us "Find Us"
        :countdown "Countdown"
        :accomodation "Accommodation"
        :contacts "Contacts"}

   :it {:timeline "Programma"
        :story "La Nostra Storia"
        :find-us "Trovaci"
        :countdown "Conto Alla Rovescia"
        :accomodation "Dove Dormire"
        :contacts "Contatti"}})

(defn navbar
  [sections]
  (into [:div.navbar__container]
        (cons
         [:a.navbar__backlink {:href "/"}]
         (for [sec sections]
           [:div.navbar__link
            [:a {:href (str "#" (name sec))}
             (translate navbar-dict sec)]]))))

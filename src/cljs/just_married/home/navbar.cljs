(ns just-married.home.navbar
  (:require [just-married.home.language :refer [translate get-language]]))

(def ^:private navbar-dict
  {:en {:home "Home"
        :timeline "Timeline"
        :rvsp "RVSP"
        :story "Our Story"
        :find-us "Find Us"
        :countdown "Countdown"
        :accomodation "Accommodation"
        :contacts "Contacts"}

   :it {:home "Home"
        :timeline "Programma"
        :story "La Nostra Storia"
        :find-us "Trovaci"
        :rvsp "RVSP"
        :countdown "Conto Alla Rovescia"
        :accomodation "Dove Dormire"
        :contacts "Contatti"}})

(defn navbar
  [sections]
  (let [sections (cons :home sections)]

    (into [:div.navbar__container]
          (for [sec sections]
            ;; could avoid the special case maybe somehow
            (let [href (if (= sec :home)
                         "#"
                         (str "#" (name sec)))]

              [:div.navbar__link
               [:a {:href href}
                (translate navbar-dict sec)]])))))


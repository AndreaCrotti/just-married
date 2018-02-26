(ns just-married.home.navbar
  (:require [just-married.home.language :refer [translate get-language]]))

(def ^:private navbar-dict
  {:en {:home "Home"
        :timeline "Timeline"
        :rvsp "RVSP"
        :story "Our Story"
        :find-us "Find Us"
        :countdown "Countdown"
        :accommodation "Accommodation"
        :contacts "Contacts"
        :gift "Wedding Registry"}

   :it {:home "Home"
        :timeline "Programma"
        :story "La Nostra Storia"
        :find-us "Trovaci"
        :rvsp "RVSP"
        :countdown "Conto Alla Rovescia"
        :accommodation "Dove Dormire"
        :contacts "Contatti"
        :gift "Lista Nozze"}})

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


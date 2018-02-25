(ns just-married.home.gift
  (:require [just-married.home.language :refer [translate]]))

(def LANGUAGE-TO-CURRENCY
  {:en "gbp"
   :it "euro"})

(def coordinates
  {:eur {:name "Andrea Crotti"
         :iban "DE70100110012629402677"}

   ;;TODO: fix the naem
   :gbp {:name "Andrea & Enrica"
         :iban "GB19ABBY09012921859069"
         :number "21859069"
         :sort-code "09-01-29"}})

(def gift-dict
  {:en {:gift "Your company in this special day will be the greatest gift. If you would like to contribute to our Honeymoon at the Seychelles you can do use these bank coordinates:"}

   :it {:gift "La vostra presenza in questo giorno speciale sarà il regalo più importante.
Se vorrete contribuire al nostro viaggio di nozze alle Seychelles potrete farlo usando queste coordinate bancarie:"}})

(def ^:private tr
  (partial translate gift-dict))

(defn gift
  []
  [:div.gift {:id "gift"}
   [:h3 "Gift"]
   (tr :gift)
   [:h4 "Euros"]
   [:ul
    [:li "NAME:" (-> coordinates :eur :name)]
    [:li "IBAN:" (-> coordinates :eur :iban)]]

   [:h4 "Pounds"]
   [:ul
    [:li "NAME:" (-> coordinates :gbp :name)]
    [:li "IBAN:" (-> coordinates :gbp :iban)]
    [:li "SORT CODE:" (-> coordinates :gbp :sort-code)]
    [:li "NUMBER:" (-> coordinates :gbp :number)]]])

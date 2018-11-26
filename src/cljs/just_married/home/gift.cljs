(ns just-married.home.gift
  (:require [just-married.home.language :refer [translate]]
            [clojure.string :as string]
            [goog.string :as gstring]))

(def LANGUAGE-TO-CURRENCY
  {:en "gbp"
   :it "euro"})

(def coordinates
  {:nome "Marco Guidetti"
   :iban "IT95U0306234210000001075832"
   :bic  "MEDBITMMXXX"
   :cab  "34210"
   :abi  "03062"
   :cin  "U"
   :bban "U030623421001075832"})

(def gift-dict
  {:it {:gift   "La vostra presenza in questo giorno speciale sarà il regalo più importante. Se vorrete contribuire a rendere la nostra casa piu accogliente, potrete farlo utilizzando queste coordinate bancarie:"
        :title  "Lista Nozze"}})

(def ^:private tr
  (partial translate gift-dict))

(defn coords-table
  [fields key]
  [:table.bank_table
   [:tbody
    (into [:tr]
          (for [f fields]
            [:th (-> f name string/upper-case)]))


    (into [:tr]
          (for [f fields]
            [:td (-> coordinates key f)]))]])

(defn gift
  []
  [:div.gift.section {:id "gift"}
   [:h3 (tr :title)]
   (tr :gift)
   (into [:ul.bank__coordinate__list]
         (for [k [:nome :iban :bic :cab :abi :cin :bban]]
           [:li
            [:span.bank__coordinate__label
             (-> k name string/upper-case)]

            [:span.bank__coordinate__value
             (-> coordinates
                 k)]]))])

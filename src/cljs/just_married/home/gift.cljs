(ns just-married.home.gift
  (:require [just-married.home.language :refer [translate]]
            [clojure.string :as string]
            [goog.string :as gstring]))

(def LANGUAGE-TO-CURRENCY
  {:en "gbp"
   :it "euro"})

;; add missing information here:
;; Marco Guidetti
;; IBAN: IT95U0306234210000001075832
;; CAB: 34210
;; ABI: 03062
;; CIN: U
;; BBAN: U030623421001075832
;; BIC/SWIFT: MEDBITMMXXX

(def coordinates
  {:eur {:name "Marco Guidetti"
         :iban "IT95U0306234210000001075832"
         :bic "MEDBITMMXXX"}})

(def gift-dict
  {:it {:gift   "La vostra presenza in questo giorno speciale sarà il regalo più importante. Se vorrete contribuire a rendere la nostra casa piu accogliente potete farlo utilizzando queste coordinate bancarie."
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
   (coords-table [:name :iban :bic] :eur)])

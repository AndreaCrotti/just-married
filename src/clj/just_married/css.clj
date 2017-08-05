(ns just-married.css
  (:require [garden.def :refer [defstyles]]))

(def COLOR-PALLETTE
  {:amaranth "#E52B50"}
  )

;; each style defines a new file, could simply also generate
;; many different files ideally
(defstyles screen
  ;; various language settings
  [:.language-group {:padding-right "20px"}]
  [:.language.selected {:padding-right "20px"}]
  [:.language.not-selected {:padding-right "20px"}]

  ;; header settings
  [:#header {:background-color "red"
             :font-color "blue"}]

  [:#header [:a {:padding-right "20px"}]]
  
  ;; this should be an id instead of this
  [:#submit-button {:background-color "red" :font-weight "bold"}]
)

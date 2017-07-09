(ns just-married.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [just-married.language :refer [make-lang AVAILABLE-LANGUAGES]]
   [just-married.payment-views :as payment-views]))

(defn lang-selection
  "Define all the possible languages as sequences of clickable images"
  []
  (let [current-language (subscribe [:current-language])]
    (fn []
      (into
       [:div {:class "language-group"}]
       (for [lang AVAILABLE-LANGUAGES]
         (make-lang lang @current-language))))))

;; devtools does not seem to be set up correctly
;; since it doesn't find hints.js for example
;; (log "hello")

(defn main-panel
  []
  (fn []
    [:g
     [lang-selection]
     [payment-views/amazon-wish-list]]
    ))

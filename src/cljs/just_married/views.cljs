(ns just-married.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [tongue.core :as tongue]))

(def AVAILABLE-LANGUAGES
  "All the available languages"
  #{:english :italian})

(def dicts
  "List of all the words/sentences that need localization"
  {:en {:welcome "Welcome"}
   :it {:welcome "Benvenuto"}})

(def translate
  (tongue/build-translate dicts))

(defn- set-lang [lang]
  (dispatch [:set-language lang]))

(def flag-files
  (zipmap AVAILABLE-LANGUAGES
          (map #(subs (str % ".png") 1) AVAILABLE-LANGUAGES)))

(defn make-lang [lang current-language]
  (let [selected (= lang current-language)
        png-file (lang flag-files)
        props {:type "image" :src png-file}
        full-props (if selected
                     (assoc props :class "language.selected" )
                     (assoc props
                            :class "language.not-selected"
                            :on-click #(dispatch [:set-language lang])))]

    [:input full-props]))


(defn lang-selection
  "Define all the possible languages as sequences of clickable images"
  []
  (let [current-language (subscribe [:current-language])]
    (fn []
      (into
       [:div {:class "language-group"}]
       (for [lang AVAILABLE-LANGUAGES]
         (make-lang lang @current-language))))))

(defn payment
  []
  "Gift section, using stripe or something similar
for the integration")

(defn main-panel
  []
  (fn []
    [:g "hello world"]
    [:g [lang-selection]]
    ))

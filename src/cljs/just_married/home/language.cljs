(ns just-married.home.language
  (:require [tongue.core :as tongue]
            [cemerick.url :refer [url]]))

(defn get-language
  []
  (-> js/window
      .-location
      .-href
      url
      :query
      (get "language" "en")
      keyword))

(defn translate
  [dict key]
  ((tongue/build-translate dict) (get-language) key))

(ns just-married.language
  (:require [clojure.string :as string]))

(def ^:private default-language :en)

(defn detect-language
  "Lookup in the request to find out what should be the default language to serve"
  [accept-language available-languages]
  (if accept-language
    (let [languages (string/split accept-language #",")
          ;; can probably be done a bit more easily
          parsed-languages (map #(-> %
                                     (string/split #",")
                                     first
                                     (string/split #"-")
                                     first
                                     keyword)
                                languages)

          only-available (filter #(contains? available-languages %) parsed-languages)]

      (or (first only-available) default-language))
    default-language))

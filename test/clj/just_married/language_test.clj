(ns just-married.language-test
  (:require [just-married.language :as sut]
            [clojure.test :as t]))

(t/deftest language-detection-test
  (t/testing "detect language from header"
    (t/are [req available detected] (= (sut/detect-language req available) detected)
      "en" #{:en :it} :en
      "en-US" #{:en :it} :en
      "it-IT" #{:en :it} :it
      "it,en-US;q=0.9,en;q=0.4" #{:en :it} :it)))

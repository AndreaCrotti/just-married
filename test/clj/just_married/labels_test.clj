(ns just-married.labels-test
  (:require [just-married.labels :as sut]
            [clojure.test :refer [deftest testing is]]))

(def sample-address
  {:group_name "Long group name"
   :country "IT"
   :address "My street 42, Town 10022 (PR)"})

(deftest gen-table-test
  (testing "gen table with one address"
    (is (= [:pdf-table
            [20 20 20]
            [[:pdf-cell
              "Long group name\nMy street 42, Town 10022 (PR)\nItaly"]]]
           (sut/gen-table [sample-address])))))

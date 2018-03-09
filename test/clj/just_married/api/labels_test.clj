(ns just-married.api.labels-test
  (:require [clojure.test :refer [deftest is testing use-fixtures]]
            [just-married.db-setup :refer [setup]]
            [just-married.db :as db]
            [just-married.api.labels :as sut]))

(use-fixtures :each setup)

(def sample-address
  {:group_name "Long group name"
   :country    "IT"
   :address    "My street 42, Town 10022 (PR)"})

(deftest fetch-labels-test
  (testing "Fetching labels work correctly"
    (let [not-invited     {:group_name "sample group"
                           :invited_by "andrea"}
          invited-already {:group_name      "sample group already invited"
                           :invited_by      "andrea"
                           :invitation_sent true}]

      (db/add-guest-group! not-invited)
      (db/add-guest-group! invited-already)

      (let [labels (sut/get-labels!)]
        (is (= [{:group_name "sample group"
                 :address    nil
                 :country    nil}] labels))))))

(deftest group-addresses-test
  (let [full sample-address
        no-address (dissoc sample-address :address)
        longer (update sample-address :group_name #(str % "Even longer name"))]

    (testing "three columns sort"
      (is (= [[longer full no-address]]
             (sut/group-addresses [full no-address longer] 3))))))

(deftest gen-table-test
  (testing "gen table with one address"
    (is (= [:pdf-table
            {:width-percent    100
             :border           false
             :horizontal-align :right}
            [10 10 10]
            [[:pdf-cell
              {:align   :right
               :padding (repeat 4 8)}
              "Long group name\nMy street 42, Town 10022 (PR)\nItaly"]]]
           (sut/gen-table [sample-address] 3)))))

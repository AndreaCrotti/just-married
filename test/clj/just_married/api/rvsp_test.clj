(ns just-married.api.rvsp-test
  (:require [just-married.api.rvsp :as sut]
            [just-married.db-setup :refer [setup]]
            [clojure.test :refer [deftest testing is use-fixtures]]))

(use-fixtures :each setup)

(deftest rvsp-api-test
  (testing "Rvsp test"
    (let [response
          (sut/rvsp! {:json-params {:name     "Name"
                                    :email    "My email"
                                    :how-many "3"
                                    :comment  "My comment"
                                    :coming   "true"}})]

      (is (= 201 (-> response :status))))))

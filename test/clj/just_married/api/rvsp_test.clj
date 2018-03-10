(ns just-married.api.rvsp-test
  (:require [just-married.api.rvsp :as sut]
            [just-married.db :as db]
            [just-married.db-setup :refer [setup]]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer [deftest testing is use-fixtures]]))

(use-fixtures :each setup)

(defn get-rvsps
  []
  (-> (h/select :*)
      (h/from :rvsp)
      (sql/format)))

(deftest rvsp-api-test
  (testing "Rvsp test"
    (let [response
          (sut/rvsp! {:json-params {:name     "Name"
                                    :email    "My email"
                                    :how-many "3"
                                    :comment  "My comment"
                                    :coming   "true"}})]

      (is (= 201 (-> response :status)))
      (let [rows (jdbc/query (db/db-spec) (get-rvsps))]
        (is (= 1 (count rows)))))))

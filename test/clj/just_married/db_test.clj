(ns just-married.db-test
  (:require [just-married.db :as db]
            [just-married.utils :refer [db-reachable? database-url]]
            [clojure.test :refer :all]
            [migratus.core :as migratus]
            [environ.core :refer [env]]
            [clojure.java.jdbc :as j])
  (:import (org.postgresql.util PSQLException)))

;; should we default to something maybe?
(def config {:store :database
             :migration-dir "migrations"
             :db database-url})

(defn setup-db [f]
  (migratus/migrate config)
  (f)
  (migratus/reset config))

;; how do I find out if the database is actually reachable?

(use-fixtures :each setup-db)

;; use a test selector instead of doing it this way
(when db-reachable?
  (deftest test-db-migrates
    (testing "check migration works"
      (is (= 1 (inc 0)))))

  (deftest test-list-all-members
    (testing "adding guest"
      (db/add-guest! {:first-name "Mario"
                      :last-name "Bros"}))

    (testing "Adding guest with a family name"
      ;; if I had a clojure spec to represent data I could the right
      ;; magic in transforming the data
      (db/add-family! {:invited-by "andrea"
                       :family-name "Plumbers"})

      (db/add-guest! {:first-name "Luigi"
                      :last-name "bros"
                      :family-name "Plumbers"})

      (let [res (j/query database-url)]
        (is (= 2 (count res)))))))

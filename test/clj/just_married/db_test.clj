(ns just-married.db-test
  (:require [just-married.db :as db]
            [just-married.utils :refer [db-reachable? database-url]]
            [clojure.test :refer :all]
            [migratus.core :as migratus]
            [clojure.java.jdbc :as j])
  (:import (org.postgresql.util PSQLException)
           (java.util UUID)))

;; should we default to something maybe?
(def config {:store :database
             :migration-dir "migrations"
             :db database-url})

(defn gen-uuid
  []
  (UUID/randomUUID))

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
      (let [group-id (gen-uuid)]

        (db/add-guest-group! {:id group-id})
        (db/add-guest! {:first-name "Mario"
                        :last-name  "Bros"
                        :group-id   group-id})))

    (testing "Adding guest with a guest-group name"
      (let [group-id (gen-uuid)]
        ;; if I had a clojure spec to represent data I could the right
        ;; magic in transforming the data
        (db/add-guest-group! {:invited-by "andrea"
                              :group-name "Plumbers"
                              :id group-id})

        (db/add-guest! {:first-name "Luigi"
                        :last-name  "bros"
                        :group-id group-id})

        (let [res (db/all-guests!)]
          (is (= 2 (count res))))))))

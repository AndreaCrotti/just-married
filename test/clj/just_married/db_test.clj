(ns just-married.db-test
  (:require [just-married.db :as db]
            [clojure.test :refer :all]
            [migratus.core :as migratus]
            [environ.core :refer [env]]
            [clojure.java.jdbc :as j])
  (:import (org.postgresql.util PSQLException)))

;; this could be defaulting already somewhere else??
;; and make it also being directly available in cider?
(def DEFAULT-TEST-DB "postgresql://just_married:just_married@localhost:5440/just_married")

(def DATABASE-URL (get env :database-url DEFAULT-TEST-DB))

;; should we default to something maybe?
(def config {:store :database
             :migration-dir "migrations"
             :db DATABASE-URL})

(defn setup-db [f]
  (migratus/migrate config)
  (f)
  (migratus/reset config))

;; how do I find out if the database is actually reachable?

(def db-reachable?
  (= (try
       (j/execute! DATABASE-URL "")
       (catch PSQLException e))
     [0]))

(use-fixtures :each setup-db)

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
                      :family-name "Plumbers"}))))

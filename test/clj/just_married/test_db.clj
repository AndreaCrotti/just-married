(ns just-married.test-db
  (:require [just-married.db :as db]
            [clojure.test :as t]
            [migratus.core :as migratus]
            [environ.core :refer [env]]
            [clojure.java.jdbc :as j]))

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
       (catch org.postgresql.util.PSQLException e))
     [0]))

(t/use-fixtures :each setup-db)

(when db-reachable?
  (t/deftest test-db-migrates
    (t/testing "check migration works"
      (t/is (= 1 (inc 0)))))

  (t/deftest test-add-confirmation
    (let [existing-person
          (db/add-person! "name" "my@mail.com")])

    (t/testing "Confirm existing person"
      (db/confirm! "name" "my@mail.com" true))))

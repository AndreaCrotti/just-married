(ns just-married.test-db
  (:require [just-married.db :as db]
            [clojure.test :as t]
            [migratus.core :as migratus]
            [environ.core :refer [env]]))

;; this could be defaulting already somewhere else??
;; and make it also being directly available in cider?
(def DEFAULT-TEST-DB "postgresql://just_married:just_married@localhost:5440/just_married")

;; should we default to something maybe?
(def config {:store :database
             :migration-dir "migrations"
             :db (get env :database-url DEFAULT-TEST-DB)})

(defn setup-db [f]
  (migratus/migrate config)
  (f)
  (migratus/reset config))

(t/use-fixtures :each setup-db)

;;TODO: add tests again when they postgres is actually correctly being
;;set up in circle ci
#_(t/deftest test-db-loads
  (t/testing "check migration works"
    (t/is (= 1 (inc 0)))))

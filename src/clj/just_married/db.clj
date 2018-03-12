(ns just-married.db
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql
             [core :as sql]
             [helpers :as h]]
            [environ.core :refer [env]]
            [honeysql-postgres.helpers :as ph]))

(def ^:dynamic override-db-spec
  nil)

(def local-db-url "postgresql://just_married:just_married@localhost:5440/just_married")
(def test-db-url "postgresql://just_married:just_married@localhost:5440/test_just_married")

(defn db-spec
  []
  (or override-db-spec (env :database-url local-db-url)))

(defn all-guests
  []
  (-> (h/select :*)
      (h/from :guest)
      (sql/format)))

(defn all-guests!
  []
  (jdbc/query (db-spec) (all-guests)))

(defn- insert-into!
  [sql-map]
  (let [query (-> sql-map
                  (ph/returning :id)
                  sql/format)]

    (jdbc/execute! (db-spec)  query)))

(defn add-guest!
  [guest]
  (insert-into!
   (-> (h/insert-into :guest)
       (h/values [guest]))))

;; TODO: move it to test namespace if this is only needed for tests
;; anyway
(defn add-guest-group!
  [guest-group]
  (insert-into!
   (-> (h/insert-into :guests-group)
       (h/values [guest-group]))))

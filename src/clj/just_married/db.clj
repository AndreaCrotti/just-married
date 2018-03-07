(ns just-married.db
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql
             [core :as sql]
             [helpers :as h]]
            [environ.core :refer [env]]
            [honeysql-postgres.helpers :as ph]
            [honeysql-postgres.format :as pf]))

(def ^:dynamic override-db-spec
  nil)

(def local-db-url "postgresql://just_married:just_married@localhost:5440/just_married")
(def test-db-url "postgresql://just_married:just_married@localhost:5440/test_just_married")

(defn db-spec
  []
  (or override-db-spec (env :database-url local-db-url)))

(defn all-guests
  []
  (->
   (h/select :*)
   (h/from :guest)
   (sql/format)))

(defn all-guests!
  []
  (jdbc/query (db-spec) (all-guests)))

(defn all-groups
  []
  (-> (h/select :*)
      (h/from :guests-group)
      (sql/format)))

(defn labels
  []
  (-> (h/select :group_name :country :address)
      (h/from :guests-group)
      (sql/format)))

(defn labels!
  []
  (jdbc/query (db-spec) (labels)))

(defn guests-by-group
  [group-id]
  (->
   (h/select :first_name :last_name)
   (h/from :guest)
   (h/where [:= :guest.group_id group-id])
   (sql/format)))

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

(ns just-married.db
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql
             [core :as sql]
             [helpers :as h]]
            [environ.core :refer [env]]
            [honeysql-postgres.helpers :as ph]
            [honeysql-postgres.format :as pf]))

(def DEFAULT-DB-URL "postgresql://just_married:just_married@localhost:5440/just_married")

(defn all-guests
  []
  (->
   (h/select :*)
   (h/from :guest)
   (sql/format)))

(defn all-guests!
  []
  (jdbc/query DEFAULT-DB-URL (all-guests)))

(defn all-groups
  []
  (->
   (h/select :*)
   (h/from :guests-group)
   (sql/format)))

(defn guests-by-group
  [group-id]
  (->
   (h/select :first-name :last-name)
   (h/from :guest)
   (h/where [:= :guest.group-id group-id])
   (sql/format)))

(defn- insert-into!
  [sql-map]
  (let [query (-> sql-map
                  (ph/returning :id)
                  sql/format)]

    (jdbc/execute! DEFAULT-DB-URL  query)))

(defn add-guest!
  [guest]
  (insert-into!
   (-> (h/insert-into :guest)
       (h/values [guest]))))

(defn add-guest-group!
  [guest-group]
  (insert-into!
   (-> (h/insert-into :guests-group)
       (h/values [guest-group]))))

(defn- get-guest
  [email]
  (->
   ;; should use select for update here to be super picky??
   (h/select :id)
   (h/from :guest)
   (h/where [:= :email-address email])
   ((sql/format))))

(defn get-guest!
  "Lookup someone by email and return its id or nil if not found"
  [email]
  (let [result
        (jdbc/query
         DEFAULT-DB-URL
         (get-guest email))]

    ;; seems like a hacky way to do this
    (when (not= result '())
      (-> result (first) :id))))

(defn- add-confirmation
  [guest-id coming]
  (->
   (h/insert-into :confirmation)
   (h/columns :confirmed-by :coming)
   (h/values [[guest-id coming]])
   (sql/format)))

(defn add-confirmation!
  [guest-id coming]
  (let [query (add-confirmation guest-id coming)]
    (jdbc/execute! DEFAULT-DB-URL query)))

(defn confirm!
  "Add to the database that someone confirmed"
  [name email coming]
  ;; if the email address is not available then how do we
  ;; link it directly to the right guest?
  (let [guest-id
        (or (get-guest! email)
            (add-guest! name email))]

    (add-confirmation! guest-id coming)))

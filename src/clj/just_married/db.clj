(ns just-married.db
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [environ.core :refer [env]]
            [honeysql.helpers :as h]
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

(defn all-families
  []
  (->
   (h/select :*)
   (h/from :family)
   (sql/format)))

(defn- list-guests
  []
  (->
   (h/select :first-name :last-name)
   (h/from :guest)
   (sql/format)))

(defn guests-by-family
  [name]
  (->
   (h/select :first-name :last-name)
   (h/from :guest)
   (h/where [:= :guest.family-name name])
   (sql/format)))

(defn- to-underscore
  [f]
  (-> f
      name
      (clojure.string/replace "-" "_")))

(defn- insert-into!
  [data table]
  (jdbc/insert! DEFAULT-DB-URL
                table
                (map to-underscore (keys data))
                (vals data)))

(defn add-guest!
  [guest]
  (insert-into! guest :guest))

(defn add-family!
  [family]
  (insert-into! family :family))

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

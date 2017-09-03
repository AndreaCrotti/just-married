(ns just-married.db
  (:require [clojure.java.jdbc :as j]
            [honeysql.core :as honey]
            [environ.core :refer [env]]
            [honeysql.helpers :as h]
            [honeysql-postgres.helpers :as ph]
            [honeysql-postgres.format :as pf]))

(def DEFAULT-DB-URL "postgresql://just_married:just_married@localhost:5440/just_married")

(def db-conn
  (get env :database-url DEFAULT-DB-URL))

(defn- add-person
  [name email]
  (->
   (h/insert-into :guest)
   (h/columns :first_name :email-address)
   (h/values [[name email]])
   ;; some way to return the id created??
   honey/format))

(defn add-person!
  "Add a person and return the id"
  [name email]
  (j/execute! DEFAULT-DB-URL (add-person name email)))

(defn- get-person
  [email]
  (->
   ;; should use select for update here to be super picky??
   (h/select :id)
   (h/from :guest)
   (h/where [:= :email-address email])
   (honey/format)))

(defn get-person!
  "Lookup someone by email and return its id or nil if not found"
  [email]
  (let [result
        (j/query
         DEFAULT-DB-URL
         (get-person email))]

    ;; seems like a hacky way to do this
    (when (not= result '())
      (-> result (first) :id))))

(defn- add-confirmation
  [person-id coming]
  (-> 
   (h/insert-into :confirmation)
   (h/columns :confirmed-by :coming)
   (h/values [[person-id coming]])
   (honey/format)))

(defn add-confirmation!
  [person-id coming]
  (let [query (add-confirmation person-id coming)]
    (j/execute! DEFAULT-DB-URL query)))

(defn confirm!
  "Add to the database that someone confirmed"
  [name email coming]
  ;; if the email address is not available then how do we
  ;; link it directly to the right person?
  (let [person-id
        (or (get-person! email)
            (add-person! name email))]
    
    (add-confirmation! person-id coming)))

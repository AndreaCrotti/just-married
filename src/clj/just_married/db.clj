(ns just-married.db
  (:require [clojure.java.jdbc :as j]
            [honeysql.core :as honey]
            [environ.core :refer [env]]
            [honeysql.helpers :as h]))

(def DEFAULT-DB-URL "postgresql://just_married:just_married@localhost:5440/just_married")

(def db-conn
  (get env :database-url DEFAULT-DB-URL))

(defn add-person
  "Add a person and return the id"
  [name email]
  (->
   (h/insert-into :people)
   (h/columns :first_name :email)
   (h/values [[name email]])
   ;; some way to return the id created??
   (honey/format)))

(defn get-person
  "Lookup someone by email and return its id or nil if not found"
  [email]
  (->
   ;; should use select for update here to be super picky??
   (h/select :id)
   (h/from :people)
   (h/where [:= :email email])
   (honey/format)))

(defn add-confirmation
  [person-id coming]
  (-> 
   (h/insert-into :confirmation)
   (h/columns :confirmed-by :coming)
   (h/values [[person-id coming]])))


(defn confirm!
  "Add to the database that someone confirmed"
  [name email coming]
  ;; if the email address is not available then how do we
  ;; link it directly to the right person?
  (let [person-id
        (or (get-person email)
            (j/execute! (add-person name email)))]

    (j/execute! (add-confirmation person-id coming))))

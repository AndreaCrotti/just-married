(ns just-married.db
  (:require [clojure.java.jdbc :as j]
            [honeysql.core :as honey]
            [environ.core :refer [env]]))

(def DEFAULT-DB-URL "postgresql://just_married:just_married@localhost:5440/just_married")

(def db-conn
  (get env :database-url DEFAULT-DB-URL))

(def all-people
  {:select [:first-name] :from [:people]})

;; sample queries
;; (j/execute! db-conn ["TRUNCATE TABLE people CASCADE"])
;; (j/query db-conn (honey/format all-people))

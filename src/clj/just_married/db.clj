(ns just-married.db
  (:require [clojure.java.jdbc :as j]
            [honeysql.core :as honey]
            [environ.core :refer [env]]
            [honeysql.helpers :as helpers]))

(def DEFAULT-DB-URL "postgresql://just_married:just_married@localhost:5440/just_married")

(def db-conn
  (get env :database-url DEFAULT-DB-URL))

(def all-people
  {:select [:first-name] :from [:people]})


;; sample queries
;; (j/execute! db-conn ["TRUNCATE TABLE people CASCADE"])
;; (j/query db-conn (honey/format all-people))

;; (-> (helpers/insert-into :family)
;;     (helpers/columns :family-name :second)
;;     (helpers/values
;;      [["hello" "world"]])
;;     honey/format)

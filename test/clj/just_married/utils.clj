(ns just-married.utils
  (:require [clojure.java.jdbc :as j]
            [environ.core :refer [env]])
  (:import (org.postgresql.util PSQLException)))

(def default-test-db "postgresql://just_married:just_married@localhost:5440/just_married")

(def database-url (get env :database-url default-test-db))

(def db-reachable?
  (= (try
       (j/execute! database-url "")
       (catch PSQLException e))
     [0]))

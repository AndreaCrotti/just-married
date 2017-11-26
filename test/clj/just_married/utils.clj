(ns just-married.utils
  (:require [clojure.java.jdbc :as j]
            [environ.core :refer [env]])
  (:import (org.postgresql.util PSQLException)))

;; this could be defaulting already somewhere else??
;; and make it also being directly available in cider?
(def DEFAULT-TEST-DB "postgresql://just_married:just_married@localhost:5440/just_married")

(def DATABASE-URL (get env :database-url DEFAULT-TEST-DB))

(def db-reachable?
  (= (try
       (j/execute! DATABASE-URL "")
       (catch PSQLException e))
     [0]))

(ns just-married.test-db
  (:require [just-married.db :as db]
            [clojure.test :as t]
            [migratus.core :as migratus]))

(defn my-test-fixture [f]
  ;; add the right migratus functions to create and destroy the
  ;; database, before running the tests
  ;;(create-db)
  (f)
  #_(destroy-db))

; Here we register my-test-fixture to be called once, wrapping ALL tests 
; in the namespace
(t/use-fixtures :once my-test-fixture)

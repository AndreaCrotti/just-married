(ns just-married.db-setup
  (:require [just-married.db :as db]
            [environ.core :refer [env]]
            [migratus.core :as migratus]
            [clojure.java.jdbc :as jdbc])

  (:import (org.postgresql.util PSQLException)))

(defn wrap-db-tx
  [test-fn]
  (jdbc/with-db-transaction [tx db/test-db-url
                             {:isolation :repeatable-read}]

    (jdbc/db-set-rollback-only! tx)
    (binding [db/override-db-spec tx]
      (test-fn))))

(defn setup
  [test-fn]
  (if (:ci env)
    (constantly true)
    (let [config
          {:store         :database
           :migration-dir "migrations"
           :db            db/test-db-url}]

      (migratus/migrate config)
      (wrap-db-tx test-fn)
      (migratus/reset config))))

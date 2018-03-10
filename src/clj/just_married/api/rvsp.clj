(ns just-married.api.rvsp
  (:require [just-married.db :as db]
            [clojure.walk :refer [keywordize-keys]]
            [clojure.spec.alpha :as s]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [clojure.java.jdbc :as jdbc]))

(defn- coming?
  [coming-str]
  (= coming-str "true"))

(defn- how-many-cast
  [how-many-str]
  (Integer. how-many-str))

(defn rvsp-insert-sql
  [{:keys [coming how-many] :as params}]
  (-> (h/insert-into :rvsp)
      ;; (h/columns :name :email :how-many :coming :phone-number :comment)
      (h/values [(assoc params
                        :coming (coming? coming)
                        :how-many (how-many-cast how-many))])
      (sql/format)))

(defn rvsp!
  [request]
  ;;TODO: should mabye just return 400 if parameters are missing or
  ;;are not the right type at the beginning
  (let [params     (-> request :json-params keywordize-keys)
        insert-sql (rvsp-insert-sql params)]

    ;;TODO:  check for exceptions or the return code of the write maybe?
    (jdbc/execute! (db/db-spec) insert-sql)
    {:status 201
     :body   "Thanks for responding"}))

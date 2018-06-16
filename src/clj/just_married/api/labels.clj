(ns just-married.api.labels
  (:require [clj-pdf.core :as pdf]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.java.jdbc :as jdbc]
            [ring.util.response :as resp]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [just-married.auth :refer [with-basic-auth]]
            [just-married.db :as db]))

(def ^:private default-n-cols 3)
(def ^:private file-name "labels.pdf")
(def ^:private default-font "Helvetica")

(def labels-sql
  (-> (h/select :group_name :country :address)
      (h/from :guests-group)
      (h/where [:and
                [:= :invitation_sent false]])
      (sql/format)))

(defn get-labels!
  []
  (jdbc/query (db/db-spec) labels-sql))

(def pdf-options
  {:title                  "Address List"
   :left-margin            10
   :right-margin           10
   :top-margin             8
   :bottom-margin          8
   :size                   :a4
   :font                   {:size     12
                            :family   default-font
                            :encoding :unicode}

   :register-system-fonts? true})

(def table-options
  {:width-percent    100
   :border           false
   :horizontal-align :right})

(def cell-options
  {:align   :right
   :padding (repeat 4 8)})

(def countries-mappping
  {"IT" "Italy"
   "UK" "United Kingdom"
   "US" "United States"})

(defn- tot-chars
  [address]
  (apply + (map count (vals address))))

(defn format-address
  [{:keys [group_name address]}]
  (format "%s\n%s" group_name (or address "")))

(defn group-addresses
  [addresses n-cols]
  (->> addresses
       (sort-by tot-chars)
       (reverse)
       (partition-all n-cols)))

(defn gen-table
  [addresses n-cols]
  (let [grouped-addresses (group-addresses addresses n-cols)]
    (into [:pdf-table
           table-options
           (repeat n-cols 10)]
          (map #(for [addr %]
                  [:pdf-cell cell-options (format-address addr)])
               grouped-addresses))))

(defn labels
  "Place all the labels in a table generating the right pdf code"
  [addresses]
  (pdf/pdf
   [pdf-options
    (gen-table addresses default-n-cols)]
   file-name)

  file-name)

(defn labels-api
  [request]
  (with-basic-auth request
    (let [labels-data     (get-labels!)
          labels-pdf-file (labels labels-data)]

      (-> (resp/file-response labels-pdf-file)
          (resp/content-type "application/pdf")))))

(ns just-married.labels
  (:require [clj-pdf.core :as pdf]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [ring.util.response :as resp]
            [just-married.auth :refer [with-basic-auth]]
            [just-married.db :as db]))

(def ^:private default-n-cols 3)
(def ^:private file-name "labels.pdf")

(def pdf-options
  {:title                  "Address List"
   :left-margin            2
   :right-margin           2
   :top-margin             2
   :bottom-margin          2
   :size                   :a4
   :font                   {:size     12
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
  [{:keys [group_name country address]}]
  {:pre [(or (nil? country)
             (contains? (set (keys countries-mappping)) country))]}

  (format "%s\n%s\n%s"
          group_name
          (or address "")
          (or (countries-mappping country) "")))

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
    (let [labels-data     (db/labels!)
          labels-pdf-file (labels labels-data)]

      (-> (resp/file-response labels-pdf-file)
          (resp/content-type "application/pdf")))))

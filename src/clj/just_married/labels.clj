(ns just-married.labels
  (:require [clj-pdf.core :as pdf]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def ^:private n-cols 3)
(def ^:private file-name "labels.pdf")

(def table-options
  {:width-percent    100
   :horizontal-align :right})

(def cell-options
  {:align :right})

(def countries-mappping
  {"IT" "Italy"
   "GB" "United Kingdom"
   "US" "United States"})

(defn- tot-chars
  [address]
  (apply + (map count (vals address))))

(defn format-address
  [{:keys [group_name country address]}]
  ;;TODO: add some defaults or make sure it's all populated?
  #_{:pre [(contains? (set (keys countries-mappping)) country)]}
  (format "%s\n%s\n%s"
          group_name
          (or address "")
          (or (countries-mappping country) "")))

(defn group-addresses
  [addresses]
  (->> addresses
       (sort-by tot-chars)
       (reverse)
       (partition-all n-cols)))

(defn gen-table
  [addresses]
  (let [grouped-addresses (group-addresses addresses)]
    (into [:pdf-table
           table-options
           (repeat n-cols 20)]
          (map #(for [addr %]
                  [:pdf-cell cell-options (format-address addr)])
               grouped-addresses))))

(defn labels
  "Place all the labels in a table generating the right pdf code"
  [addresses]
  ;; (fn [out])
  ;; (with-open [wtr (io/writer out)])
  (pdf/pdf
   [{}
    (gen-table addresses)]
   file-name)

  file-name)

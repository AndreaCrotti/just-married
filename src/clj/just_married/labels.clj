(ns just-married.labels
  (:require [clj-pdf.core :as pdf]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def ^:private n-cols 3)
(def ^:private file-name "labels.pdf")

(def countries-mappping
  {"IT" "Italy"
   "GB" "United Kingdom"
   "US" "United States"})

(defn format-address
  [{:keys [group_name country address]}]
  ;;TODO: add some defaults or make sure it's all populated?
  #_{:pre [(contains? (set (keys countries-mappping)) country)]}
  (format "%s\n%s\n%s"
          group_name
          address
          (countries-mappping country)))

(defn gen-table
  [addresses]
  (let [grouped-addresses (partition-all n-cols addresses)]
    (into [:pdf-table (repeat n-cols 20)]
          (map #(for [addr %]
                  [:pdf-cell (format-address addr)])
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

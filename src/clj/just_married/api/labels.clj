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

(defn labels-sql
  []
  (-> (h/select :group_name :country :address)
      (h/from :guests-group)
      (h/where [:and
                [:= :invitation_sent false]
                ;; could add this if we don't need labels for manually handed over anyway
                ;; [:not= :address nil]
                ;; [:not= :country nil]
                ])
      (sql/format)))

(defn get-labels!
  []
  (jdbc/query (db/db-spec) (labels-sql)))

(def pdf-options
  {:placecards {:title "Place Cards"
                :left-margin 3
                :right-margin 3
                :top-margin 3
                :bottom-margin 3
                :size [450 450]
                :font {:size 17
                       :ttf-name "Satisfy-Regular.ttf"
                       :encoding :unicode}
                :register-system-fonts? true}

   :addresses {:title                  "Address List"
               :left-margin            10
               :right-margin           10
               :top-margin             8
               :bottom-margin          8
               :size                   :a4
               :font                   {:size     12
                                        :family   default-font
                                        ;;TODO: change the font in this way if you want to
                                        ;; :ttf-name "resources/public/fonts/OpenSans-Regular.ttf"
                                        :encoding :unicode}
               :register-system-fonts? true}})

(def table-options
  {:width-percent    100
   :border           false
   :horizontal-align :right})

(def table-options-cards
  {:width-percent 100
   :border-width 0.2
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

(defn gen-placecard-table
  [names n-cols]
  
  (let [names-sorted (sort names)
        grouped-names (partition-all n-cols names-sorted)]

    (into [:pdf-table
           table-options-cards
           (repeat n-cols 10)]
          
          (map #(for [name %]
                  [:pdf-cell cell-options name])

               grouped-names))))

(defn labels
  "Place all the labels in a table generating the right pdf code"
  [addresses]
  (pdf/pdf
   [(:addresses pdf-options)
    (gen-table addresses default-n-cols)]
   file-name)

  file-name)

(defn placecard-generator
  [names]
  (pdf/pdf
   [(:placecards pdf-options)
    (gen-placecard-table names 3)]
   "cards.pdf")

  "cards.pdf")

(defn massage-word
  [word]
  (clojure.string/join " "
                       (map clojure.string/capitalize
                            (clojure.string/split word #" "))))

(defn write-cards
  [names-files]
  (placecard-generator (->> names-files
                           slurp
                           clojure.string/split-lines
                           (map massage-word))))

(defn labels-api
  [request]
  (with-basic-auth request
    (let [labels-data     (get-labels!)
          labels-pdf-file (labels labels-data)]

      (-> (resp/file-response labels-pdf-file)
          (resp/content-type "application/pdf")))))

(comment
  (write-cards "names.txt"))

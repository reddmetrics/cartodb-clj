(ns cartodb.client
  (:use [cascalog.api]
        [clojure.data.json :only [read-json]])
  (:require [clojure.contrib.str-utils :as str-utils]
            [clj-http.client :as client]
            [cheshire.core :as json]))

(defn url-base [account]
  (let [api-sql ".cartodb.com/api/v2/sql"]
    (str "http://" account api-sql)))

(defn sqlize
  [x]
  (cond
   (keyword? x) (str-utils/re-sub #"\/" "." (str-utils/re-sub #"^:" "" (str x)))
   (string? x) (str "'" x "'")
   :else x))

(defn sql-exp
  [op p1 p2]
  (str (sqlize p1) " " op " " (sqlize p2)))

(defn cartodb-collection [account sql]
  (let [base (url-base account)]
    (->> (client/get base {:query-params {"q" sql}})
         :body
         read-json
         :rows)))

;; (defn grab-forma-pts [n]
;;   (let [table (select :forma_cdm [:x :y])
;;         cdb-data (cartodb-collection "wri-01" table)
;;         grab-data (fn [key-vec] (map (comp read-string key-vec) cdb-data))]
;;     (map (partial take n)
;;          (map grab-data [:x :y]))))


(ns cartodb-clj.core
  (:use [cascalog.api]
        [clojure.data.json :only [read-json]])
  (:require [clojure.contrib.str-utils :as str-utils]
            [clj-http.client :as client]
            [cheshire.core :as json]))

;; Much of this namespace has been shamelessly lifted from the blog
;; post found [here](http://goo.gl/4m69q).  

(defn url-base [account]
  (let [api-sql ".cartodb.com/api/v2/sql"]
    (str "http://" account api-sql)))

(defn- sqlize
  [x]
  (cond
   (keyword? x) (str-utils/re-sub #"\/" "." (str-utils/re-sub #"^:" "" (str x)))
   (string? x) (str "'" x "'")
   :else x))

(defn select*
  [table & xs]
  (if (vector? (last xs))
    (let [tbls (interpose ", " (map #(sqlize %) (conj (butlast xs) table)))
          cols (str-utils/str-join "," (map #(sqlize %) (last xs)))]
      (apply str "SELECT " cols " FROM " tbls))
    (let [tbls (interpose ", " (map #(sqlize %) (conj xs table)))]
      (apply str "SELECT * FROM " tbls))))

(defmacro select
  [table & xs]
  `(let [~'as (fn[name# col#] (str (sqlize col#) " AS " (sqlize name#)))]
     (select* ~table ~@xs)))

(defn- sql-exp
  [op p1 p2]
  (str (sqlize p1) " " op " " (sqlize p2)))

(defmacro filter-sql
  [pred query]
  `(let [~'and  (fn[& xs#] (apply str (interpose " AND " xs#)))
         ~'or   (fn[& xs#] (apply str (interpose " OR " xs#)))
         ~'=    (fn[x# y#] (sql-exp "=" x# y#))
         ~'>    (fn[x# y#] (sql-exp ">" x# y#))
         ~'<    (fn[x# y#] (sql-exp "<" x# y#))
         ~'like (fn[x# y#] (sql-exp "like" x# y#))
         ~'in   (fn[x# xs#]
                (str (sqlize x#) " IN (" (apply str (interpose ", " xs#)) ")"))]
     (apply str ~query " WHERE " ~pred)))

(defn cartodb-collection [account sql]
  (let [base (url-base account)]
    (->> (client/get base {:query-params {"q" sql}})
         :body
         read-json
         :rows)))

(defn grab-forma-pts [n]
  (let [table (select :forma_cdm [:x :y])
        cdb-data (cartodb-collection "wri-01" table)
        grab-data (fn [key-vec] (map (comp read-string key-vec) cdb-data))]
    (map (partial take n)
         (map grab-data [:x :y]))))


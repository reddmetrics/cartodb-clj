(ns cartodb.client
  (:use [clojure.data.json :only [read-json]])
  (:require [clojure.contrib.str-utils :as s]
            [clj-http.client :as client]
            [cheshire.core :as json]))

(defn url-base
  "returns a CartoDB http address base an SQL query, given a CartoDB
  account name

  Example usage:
  (url-base \"wri-01\") => \"http://wri-01.cartodb.com/api/v2/sql\""
  [account]
  (let [api-sql ".cartodb.com/api/v2/sql"]
    (str "http://" account api-sql)))

(defn sqlize
  "returns a string that is compatible with SQL queries, based on
  clojure-style inputs (e.g., keywords and strings). Shamelessly
  lifted from the blog post found [here](http://goo.gl/4m69q)."
  [x]
  (cond
   (keyword? x) (s/re-sub #"\/" "." (s/re-sub #"^:" "" (str x)))
   (string? x) (str "'" x "'")
   :else x))

(defn query
  "returns CartoDB records based on the `sql` query (input as a
  string) as an array of clojure dictionaries, with the columns as
  keys and the values as strings."
  [account sql]
  (let [base (url-base account)]
    (->> (client/get base {:query-params {"q" sql}})
         :body
         read-json
         :rows)))

(defn str-append
  "append multiple strings, with spaces interposed."
  [& strs]
  (apply str (interpose " " strs)))

(defn grab-forma-pts
  "returns a vector of x- and y-coordinates for `n` forma points in
  Indonesia, with probability greater than 50 (the only points stored
  in the `forma_cdm` table)."
  [n]
  (let [sql (str-append "SELECT x,y"
                        "FROM forma_cdm"
                        "LIMIT" n)
        cdb-data (query "wri-01" sql)]
    (map (comp vec (partial map read-string) vals)
         cdb-data)))



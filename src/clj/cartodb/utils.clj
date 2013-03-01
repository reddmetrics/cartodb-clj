(ns cartodb.utils
  (:require [clojure.contrib.str-utils :as s]
            [clojure.string :as string]))

(defn sqlize
  "Returns a SQL-acceptable argument, depending on the data type.
  Defaults to identity.  See tests for example usage."
  [x]
  (cond
   (keyword? x) (s/re-sub #"\/" "." (s/re-sub #"^:" "" (str x)))
   (string? x) (str "'" x "'")
   :else x))

(defn str-sep
  "Returns a single string that is comprised of an arbitrary number of
  supplied strings, comma separated."
  [sep & strs]
  (apply str (string/join sep strs)))

(defn vec->str
  "Converts a Clojure persistent vector to a list that can be included
  in an SQL statement."
  [v]
  (let [x (map sqlize v)
        y (map str (interpose ", " x))]
    (str "(" (apply str y) ")")))

(defn insert-rows-cmd
  "Returns an SQL statement for a very simple command to insert
  multiple rows into the supplied table.

  Example Usage:
  (insert-rows-cmd \"table\" [:x :y] [2 3] [4 5])
  ;=> \"INSERT INTO table (x, y) VALUES (2, 3), (4, 5)\""
  [table col-keys & rows]
  (let [col-names (apply str-sep ", " (map sqlize col-keys))
        cols (str "(" col-names ")")
        prelude (str "INSERT INTO " table " " cols " VALUES ")]
    (str prelude (apply str-sep ", " (map vec->str rows)))))

(defn map->insert-sql
  "Convert a map to an insert SQL statement where column names are given by map
   keywords.
   Example Usage:
     (map->insert-sql \"table\" {:age 22 :year 2013})
     ;=> INSERT INTO table (age, year) VALUES (22, 2013)"
  [table m]
  (let [cols (keys m)
        vals (map m (keys m))]
    (insert-rows-cmd table cols vals)))

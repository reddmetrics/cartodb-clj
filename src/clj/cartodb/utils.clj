(ns cartodb.utils
  (:require [clojure.contrib.str-utils :as s]))

(defn sqlize
  "Returns a SQL-acceptable argument, depending on the data type.
  Defaults to identity."
  [x]
  (cond
   (keyword? x) (s/re-sub #"\/" "." (s/re-sub #"^:" "" (str x)))
   (string? x) (str "'" x "'")
   :else x))

(defn sql-builder
  "Returns a single string that is comprised of an arbitrary number of
  supplied strings, comma separated."
  [& strs]
  (apply str (interpose ", " strs)))

(defn vec->str
  "Converts a Clojure persistent vector to a list that can be included
  in an SQL statement."
  [v]
  (let [x (map sqlize v)
        y (map str (interpose ", " x))]
    (str "(" (apply str y) ")")))

(defn insert-rows-cmd
  "Returns an SQL statement for a very simple command to insert
  multiple rows into the supplied table."
  [table col-names & rows]
  (let [cols (str "(" (apply str (interpose "," col-names)) ")")
        prelude (str "INSERT INTO " table " " cols " VALUES ")]
    (str prelude (apply sql-builder (map vec->str rows)))))



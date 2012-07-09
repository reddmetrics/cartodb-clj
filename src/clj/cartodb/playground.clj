(ns cartodb.playground
  (:use [cartodb.core]
        [cartodb.utils]))

;; NOTE: Before you can run these commands, you'll need the following
;; in place:

;; (1) A local variable `creds` defined as in the README.
;; (2) A private CartoDB table with at least two columns, with names
;; "x" (a numeric variable) and "y" (string).  

;; The following queries will clear the CartoDB table, and then insert
;; two rows.  


(defn delete-all
  "Delete all rows from the specified table.  CAREFUL with this.

  Example usage:
  (delete-all creds \"cartodbclj_test\")"
  [creds table]
  (let [sql (space-sep "DELETE FROM" table)]
    (query sql :oauth creds :return false)))

(defn insert-rows
  "Insert an arbitrary number of rows into a pre-existing table and
  suppress server-side response.

  Example usage:
  (insert-rows creds 'cartodbclj_test' [:x :y] [1 \"y1\"] [2 \"y2\"])"
  [creds table column-names & rows]
  (let [sql (apply insert-rows-cmd table column-names rows)]
    (query sql :oauth creds :return false)))

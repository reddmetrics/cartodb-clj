(ns cartodb.playground
  (:use [cartodb.client]
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
  (delete-all creds \"clj_test\")"
  [oauth table]
  (let [sql (str "DELETE FROM " table)]
    (oauth-execute sql oauth :return false)))

(defn insert-rows
  "Insert an arbitrary number of rows into a pre-existing table and
  suppress server-side response.

  Example usage:
  (insert-rows creds 'clj_test' ['x' 'y'] [1 'y1'] [2 'y2'])"
  [oauth table column-names & rows]
  (let [sql (apply insert-rows-cmd table column-names rows)]
    (oauth-execute sql oauth :return false))))

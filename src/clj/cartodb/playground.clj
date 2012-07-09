(ns cartodb.playground
  "This namespace gives examples of how to use the API to compose
application-specific queries to CartoDB.  These are not tests because
we do not include our private credentials in the public repo.  Once
`creds` are defined, as below, the commands will clear a pre-existing
CartoDB table and then add rows to it.

(def creds {:key \"CARTODB_OAUTH_KEY\"
            :secret \"CARTODB_OAUTH_SECRET\"
            :user \"CARTODB_USER\"
            :password \"CARTODB_PASSWORD\"})

NOTE: Before you can run these commands, you'll need the following
in place:

(1) A local variable `creds` defined as in the README.
(2) A private CartoDB table with at least two columns, with names
`x` (a numeric variable) and `y` (string).  

The following queries will clear the CartoDB table, and then insert
two rows."
  (:use [cartodb.core]
        [cartodb.utils]))

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
  (insert-rows creds \"cartodbclj_tes\"' [:x :y] [1 \"y1\"] [2 \"y2\"])"
  [creds table column-names & rows]
  (let [sql (apply insert-rows-cmd table column-names rows)]
    (query sql :oauth creds :return false)))

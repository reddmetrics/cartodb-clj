(ns cartodb.test.client
  (:use [cartodb.client] :reload)
  (:use [midje sweet cascalog]))

(fact
  (let [sql (sql-stmt "SELECT x,y"
                      "FROM forma_cdm"
                      "LIMIT 10")]
    (count (cartodb-collection "wri-01" sql)) => 10))


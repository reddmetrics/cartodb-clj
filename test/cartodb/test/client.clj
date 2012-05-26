(ns cartodb.test.client
  (:use [cartodb.client] :reload)
  (:use [midje sweet cascalog]))

(fact
  (let [sql "SELECT x,y FROM forma_cdm"]
    (count (cartodb-collection "wri-01" sql)) => 200009))


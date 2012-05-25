(ns cartodb-clj.test.core
  (:use [cartodb-clj.core] :reload)
  (:use [midje sweet cascalog]))

(fact
  (let [table (select :forma_cdm [:x :y])]
    (filter-sql
     (and (> :prob 50) (= :cntry "Indonesia"))
     table) => "SELECT x,y FROM forma_cdm WHERE prob > 50 AND cntry = 'Indonesia'"))


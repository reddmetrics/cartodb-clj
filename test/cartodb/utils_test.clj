(ns cartodb-test.utils
  "This namespace provides unit test coverage for the cartodb.utils
namespace, which is mostly supporting functions to build SQL queries.
It's not entirely clear that this should remain in the cartodb-clj
project."
  (:use cartodb.utils)
  (:use [midje sweet]))

(fact
  (vec->str [2 "a" 4]) => "(2, 'a', 4)")

(fact
  (let [strs (map vec->str [["a" 1] ["b" 2]])]
    strs => '("('a', 1)" "('b', 2)")
    (apply sql-builder strs) => "('a', 1), ('b', 2)"))

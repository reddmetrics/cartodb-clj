(ns cartodb-test.utils
  "This namespace provides unit test coverage for the cartodb.utils
namespace, which is mostly supporting functions to build SQL queries.
It's not entirely clear that this should remain in the cartodb-clj
project."
  (:use cartodb.utils)
  (:use [midje sweet]))

(fact "Check clojure to SQL conversion"
  (map sqlize ["a" 2 :b]) => '("'a'" 2 "b"))

(fact "Check clojure vector to SQL format"
  (vec->str [2 "a" 4]) => "(2, 'a', 4)")

(fact "Check row builder for insert into SQL table"
  (let [strs (map vec->str [["a" 1] ["b" 2]])]
    strs => '("('a', 1)" "('b', 2)")
    (apply str-sep ", " strs) => "('a', 1), ('b', 2)"))

(fact "Check command for SQL row insert"
  (insert-rows-cmd "table" [:x :y] [2 3] [4 5])
  => "INSERT INTO table (x,y) VALUES (2, 3), (4, 5)")

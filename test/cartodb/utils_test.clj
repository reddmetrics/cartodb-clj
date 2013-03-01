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
  => "INSERT INTO table (x, y) VALUES (2, 3), (4, 5)")

(fact "Check that `fields-match?` correctly detects maps that with different keywords."
  (kws-match? {:a 1} {:b 2}) => false
  (kws-match? {:a 1} {:a 2}) => true)

(fact "Check maps->insert-sql"
  (maps->insert-sql "table" {:age 22 :year 2013} {:age 21 :year 1999}) =>
  "INSERT INTO table (age, year) VALUES (22, 2013), (21, 1999)"

  ;; mismatched map fields
  (maps->insert-sql "table" {:age 22 :year 2013} {:age 21 :year 2012} {:cats 2}) => (throws java.lang.AssertionError))

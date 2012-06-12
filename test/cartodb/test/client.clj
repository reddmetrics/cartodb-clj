(ns cartodb.test.client
  (:use [cartodb.client] :reload)
  (:use [midje sweet cascalog]
        clojure.test))

(deftest test-limit
  (let [sql (str-append "SELECT x,y"
                        "FROM forma_cdm"
                        "LIMIT 10")]
    (is (= 10 (count (query "wri-01" sql))))))


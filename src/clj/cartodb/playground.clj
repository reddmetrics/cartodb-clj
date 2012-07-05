(ns cartodb.playground
  (:use [cartodb.client]))

(defn try-it []
  (insert-rows creds "clj_test" ["x" "y"]
               ["x1" "y1"] ["x2" "y2"]))

(defn try-delete []
  (delete-all creds "clj_test"))

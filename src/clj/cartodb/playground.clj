(ns cartodb.playground
  (:use [cascalog.api]
        [cartodb.client]))

(def my-src (vec (grab-forma-pts 100)))

(defn casc-test []
  (?<- (stdout)
       [?x ?y]
       (my-src ?x ?y)
       (< ?x 50149)
       (> ?y 31882)))

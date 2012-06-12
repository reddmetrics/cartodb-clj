(ns cartodb.playground
  (:use [cascalog.api]
        [cartodb.client]
        [cartodb.cluster])
  (:require [incanter.core :as i]))

(defn casc-test [n]
  (let [src (vec (grab-forma-pts n))]
    (??<- [?x ?y] (src ?x ?y))))

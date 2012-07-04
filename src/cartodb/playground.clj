(ns cartodb.playground
  (:use [cascalog.api]
        [cartodb.client])
  (:require [incanter.core :as i]))

(defn casc-test [n]
  (let [src (vec (grab-forma-pts n))]
    (??<- [?x ?y] (src ?x ?y))))

(def a
  (let [sql "SELECT the_geom,num_stops,boroname,borocode,shape_area FROM nyct2000 WHERE the_geom IS NOT NULL AND boroname != 'Staten Island' AND 0<num_stops"]
    (map :num_stops (query "viz2" sql))))

(def b
  (let [sql (str-append "SELECT count(*) as ct,city,city_code,floor(timestop/100) hr"
                        "FROM stop_frisk"
                        "WHERE frisked = 'Y' and city != 'STATEN IS'"
                        "GROUP BY city,city_code,hr")]
    (query "viz2" sql)))


(ns cartodb-test.core
  "This namespace provides unit test coverage for the cartodb.client namespace."
  (:use cartodb.core)
  (:use [midje sweet]))

(fact "Check sql-builder."
  (let [sql (sql-builder "SELECT x,y"
                         "FROM forma_cdm"
                         "LIMIT 10")]
    (count (:rows (query "wri-01" sql))) => 10))

(fact "Check CSV format."
  (let [sql "SELECT x,y,the_geom FROM forma_cdm LIMIT 1"]
    (query "wri-01" sql :format "csv") => "x,y,the_geom\r\n49826,31489,"))

(fact "Check GeoJSON format."
  (let [sql "SELECT x,y,the_geom FROM forma_cdm LIMIT 1"]
    (query "wri-01" sql :format "geojson") =>
    {:type "FeatureCollection",
     :features [{:type "Feature",
                 :properties {:x "49826", :y "31489"},
                 :geometry nil}]}))

(fact "Check JSON format."
  (let [sql "SELECT x,y,the_geom FROM forma_cdm LIMIT 1"]
    (:rows (query "wri-01" sql :format "json")) =>
    [{:x "49826", :y "31489", :the_geom nil}]))

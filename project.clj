(defproject cartodb-clj "1.0.0-SNAPSHOT"
  :description "Access CartoDB data from Clojure"
  :resources-path "resources"
  :repositories {"conjars" "http://conjars.org/repo/"}
  :jvm-opts ["-XX:MaxPermSize=128M"
             "-XX:+UseConcMarkSweepGC"
             "-Xms1024M" "-Xmx1048M" "-server"]
  :javac-options {:debug "true" :fork "true"}
  :plugins [[swank-clojure "1.4.0-SNAPSHOT"]
            [lein-clojars "0.9.0"]]
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [clojure-csv/clojure-csv "2.0.0-alpha1"]
                 [clj-http "0.4.1"]
                 [cheshire "4.0.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/data.json "0.1.2"]]
:dev-dependencies [[midje "1.4.0"]])

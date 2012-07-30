(defproject cartodb-clj/cartodb-clj "1.5.2" 
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clojure-csv/clojure-csv "2.0.0-alpha1"]
                 [clj-http "0.4.3"]
                 [cheshire "4.0.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/data.json "0.1.2"]
                 [cartodb-java-client "1.0.0"]]
  :source-paths ["src/clj"]
  :profiles {:dev
             {:resource-paths
              ["dev"],
              :dependencies [[midje "1.4.0"]]}}
  :repositories {"conjars" "http://conjars.org/repo/"}
  :min-lein-version "2.0.0"
  :javac-options (:debug "true" :fork "true")
  :jvm-opts ["-XX:MaxPermSize=128M"
             "-XX:+UseConcMarkSweepGC"
             "-Xms1024M"
             "-Xmx1048M"
             "-server"]
  :plugins [[lein-swank "1.4.4"] [lein-clojars "0.9.0"]]
  :description "CartoDB client.")

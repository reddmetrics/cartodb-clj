(defproject cartodb-clj "1.5.0"
  :description "CartoDB client."
  :source-path "src/clj"
  :resources-path "resources"
  :dev-resources-path "dev"
  :repositories {"conjars" "http://conjars.org/repo/"}
  :jvm-opts ["-XX:MaxPermSize=128M"
             "-XX:+UseConcMarkSweepGC"
             "-Xms1024M" "-Xmx1048M" "-server"]
  :javac-options {:debug "true" :fork "true"}
  :plugins [[lein-swank "1.4.4"]
            [lein-clojars "0.9.0"]]
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [clojure-csv/clojure-csv "2.0.0-alpha1"]
                 [clj-http "0.4.3"]
                 [cheshire "4.0.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/data.json "0.1.2"]
                 [cartodb-java-client "1.0.0"]]
:dev-dependencies [[midje "1.4.0"]])

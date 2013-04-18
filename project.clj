(defproject cartodb-clj/cartodb-clj "1.5.4" 
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clojure-csv/clojure-csv "2.0.0-alpha1"]
                 [clj-http "0.4.3"]
                 [cheshire "4.0.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/data.json "0.1.2"]

                 ;; Deps for cartodb-java-client
                 [com.fasterxml.jackson.core/jackson-annotations "2.0.2"]
                 [com.fasterxml.jackson.core/jackson-core "2.0.2"]
                 [com.fasterxml.jackson.core/jackson-databind "2.0.2"]
                 [org.scribe/scribe "1.2.3"]
                 [commons-io/commons-io "2.1"]
                 [commons-codec/commons-codec "1.6"]]
  :source-paths ["src/clj"]
  :java-source-paths ["src/jvm"]
  :min-lein-version "2.0.0"
  :description "CartoDB client."
  :aot [com.cartodb])

(ns cartodb.client
  "This namespace provides a client API to CartoDB."
  (:use [clojure.data.json :only [read-json]])
  (:require [clojure.contrib.str-utils :as s]
            [clj-http.client :as client]
            [cheshire.core :as json]))

(defn query
  "Executes a CartoDB query on the supplied account and SQL and returns the
  results in a map of columns to string values."
  [account sql & {:keys [api-key format host] 
                  :or {api-key nil format "json" host "cartodb.com"}}]
    (try 
      (let [url (str "http://" account "." host "/api/v2/sql")
            params {:query-params {"q" sql "format" format}}
            params (if api-key (assoc-in params [:api_key] api-key) params)
            response (client/get url params)
            body (:body response)]
        (if (or (= format "json") (= format "geojson"))
              (read-json body)
               body))
      (catch Exception e (str "Error: SQL=" sql ", Error=" e))))

(defn sql-builder
  "Return space-separated query."
  [& strs]
  (apply str (interpose " " strs)))

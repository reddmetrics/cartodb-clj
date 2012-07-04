(ns cartodb.client
  "This namespace provides a client API to CartoDB."
  (:use [clojure.data.json :only [read-json]])
  (:require [clojure.contrib.str-utils :as s]
            [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.java.io :as io])
  (:import [com.cartodb.impl SecuredCartoDBClient]))

(defn- oauth-get
  "Query CartoDB with supplied SQL and OAuth credentials and return response body."
  [sql format {:keys [key secret user password]}]
  (let [client (SecuredCartoDBClient. user password key secret)
        body (.executeQuery client sql)]
    body))

(defn- get
  "Query CartoDB with supplied SQL and return response body."
  [account sql api-key format host]
  (let [url (str "http://" account "." host "/api/v2/sql")
        params {:query-params {"q" sql "format" format}}
        params (if api-key (assoc-in params [:api_key] api-key) params)
        response (client/get url params)
        body (:body response)]
    body))

(defn query
  "Executes a CartoDB query and return results in specified format."
  [sql & {:keys [account api-key format host oauth-creds] 
          :or {account nil api-key nil format "json" host "cartodb.com"
               oauth-creds nil}}]
    (let [body (if oauth-creds
                 (oauth-get sql format oauth-creds)
                 (get account sql api-key format host))]          
      (try
        (if (or (= format "json") (= format "geojson"))
          (read-json body)
          body)
        (catch Exception e (str "Error: " e " Body: " body)))))

(defn sql-builder
  "Return space-separated query."
  [& strs]
  (apply str (interpose " " strs)))

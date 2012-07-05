(ns cartodb.client
  "This namespace provides a client API to CartoDB."
  (:use [clojure.data.json :only [read-json]]
        [cartodb.utils])
  (:require [clj-http.client :as client]
            [cheshire.core :as json])
  (:import [com.cartodb.impl CartoDBClient SecuredCartoDBClient]))

(defn- oauth-execute
  "Execute an SQL command with supplied OAuth credentials; returns a
  response body by default for queries.  Specify `:return false` to
  insert rows."
  [sql {:keys [key secret user password]}
   & {:keys [return] :or {return true}}]
  (let [client (SecuredCartoDBClient. user password key secret)
        body (.executeQuery client sql)]
    (if (true? return)
      body)))

(defn- get 
  "Query CartoDB with supplied SQL and return response body."
  [account sql api-key format host api-version]
  (let [url (str "http://" account "." host "/api/" api-version "/sql")
        params {:query-params {"q" sql "format" format}}
        params (if api-key (assoc-in params [:api_key] api-key) params)
        response (client/get url params)
        body (:body response)]
    body))

(defn query
  "Executes a CartoDB query and return results in specified format."
  [sql account & {:keys [api-key format host oauth api-version] 
          :or {api-key nil format "json" host "cartodb.com"
               oauth-creds nil api-version "v2"}}]
    (let [body (if oauth
                 (oauth-execute sql oauth)
                 (get account sql api-key format host api-version))]          
      (try
        (if (or (= format "json") (= format "geojson"))
          (read-json body)
          body)
        (catch Exception e (str "Error: " e " Body: " body)))))

(defn oauth-put
  "Execute an authenticated SQL command and suppressing any
  server-side response."
  [sql oauth]
  (oauth-execute sql oauth :return false))

(defn insert-rows
  "Insert an arbitrary number of rows into a pre-existing table."
  [oauth table column-names & rows]
  (let [sql (apply insert-rows-cmd table column-names rows)]
    (oauth-put sql oauth)))

(defn delete-all
  "Delete all rows from the specified table.  CAREFUL with this."
  [oauth table]
  (let [sql (str "DELETE FROM " table)]
    (oauth-execute sql oauth :return false)))



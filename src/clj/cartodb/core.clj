(ns cartodb.core
  "This namespace provides a client API to CartoDB."
  (:use [clojure.data.json :only [read-json]]
        [cartodb.utils])
  (:require [clj-http.client :as client]
            [cheshire.core :as json])
  (:import [com.cartodb.impl SecuredCartoDBClient]))

(defn- oauth-execute
  "Execute an SQL command with supplied OAuth credentials; returns a
  response body by default for queries.  Specify `:return false` to
  suppress response body."
  [sql {:keys [key secret user password]}
   & {:keys [return] :or {return true}}]
  (let [client (SecuredCartoDBClient. user password key secret)
        body (.executeQuery client sql)]
    (if (true? return)
      body)))

(defn- execute 
  "Query CartoDB with supplied SQL and return response body."
  [account sql api-key format host api-version
   & {:keys [return] :or {return true}}]
  (let [url (str "http://" account "." host "/api/" api-version "/sql")
        params {:query-params {"q" sql "format" format}}
        params (if api-key (assoc-in params [:api_key] api-key) params)
        response (client/get url params)]
    (if (true? return)
      (:body response))))

(defn query
  "Executes a CartoDB query and return results in specified format."
  [sql & {:keys [account api-key format host oauth api-version return] 
          :or {account nil api-key nil format "json" host "cartodb.com"
               oauth-creds nil api-version "v2" return true}}]
  (let [body (if oauth
               (oauth-execute sql oauth :return return)
               (if-not (nil? account)
                 (execute account sql api-key format host api-version :return return)
                 (throw
                  (Throwable. "Enter an account name for a public table
                               or credentials for a private table."))))]          
    (try
      (if (and
           (true? return)
           (or (= format "json") (= format "geojson")))
        (read-json body)
        body)
      (catch Exception e (str "Error: " e " Body: " body)))))

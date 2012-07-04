# About

A simple Clojure client for [CartoDB](http://cartodb.com) with OAuth support. It's available on [Clojars](https://clojars.org/cartodb-clj) as a dependency for both Leinigen and Maven:

### Leiningen

```clojure
[cartodb-clj "1.1.1-SNAPSHOT"]
```

### Maven

```xml
<dependency>
  <groupId>cartodb-clj</groupId>
  <artifactId>cartodb-clj</artifactId>
  <version>1.1.1-SNAPSHOT</version>
</dependency>
```

# Usage

```clojure
(ns example
  (:use cartodb.client :as cdb))

;; Query a public table and get JSON results:
(cdb/query "SELECT * FROM table" "user")

;; Query a public table using API v1 and get JSON results:
(cdb/query "SELECT * FROM table" "user" :api-version "v1")

;; Query a public table and get GeoJSON results:
(cdb/query "SELECT * FROM table" "user" :format "geojson")

;; Query a public table and get CSV results:
(cdb/query "SELECT * FROM table" "user" :format "csv")

;; Query a public table on your own host:
(cdb/query "SELECT * FROM table" "user" :host "myserver.com")

;; Query a private table with OAuth credentials:
(def creds {:key "CARTODB_OAUTH_KEY",
            :secret "CARTODB_OAUTH_SECRET",
            :user "CARTODB_USER",
            :password "CARTODB_PASSWORD"})

(cdb/query "SELECT * FROM private_table" "user" :oauth creds)
```

## License

Copyright (C) 2012

Distributed under the Eclipse Public License, the same as Clojure.
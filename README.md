# About

This is a very simple Clojure library for the [CartoDB](http://cartodb.com) Maps and SQL API. It includes support for authentication via CartoDB API tokens and OAuth.

![](http://i.imgur.com/3e4n0.png)

# Examples

Here's a few usage examples:


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
(def creds {:key "CARTODB_OAUTH_KEY"
            :secret "CARTODB_OAUTH_SECRET"
            :password "CARTODB_PASSWORD"})

(cdb/query "SELECT * FROM private_table" "user" :oauth creds)
```

# Dependencies

The project is available on [Clojars](https://clojars.org/cartodb-clj) as a dependency for both Leinigen:

```clojure
[cartodb-clj "1.5.2"]
```

and Maven:

```xml
<dependency>
  <groupId>cartodb-clj</groupId>
  <artifactId>cartodb-clj</artifactId>
  <version>1.5.2</version>
</dependency>
```

# About the software

This software is designed and built by [REDD Metrics](http://www.reddmetrics.com). It's written in the [Clojure](http://clojure.org) programming language.

# Contributors

- Dan Hammer [@danhammer](https://github.com/danhammer)
- Robin Kraft [@robinkraft](https://github.com/robinkraft)
- Sam Ritchie [@sritchie](https://github.com/sritchie)
- Aaron Steele [@eightysteele](https://github.com/eightysteele)

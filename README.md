# About

The cartodb-clj project lets you access [CartoDB](http://cartodb.com) data from Clojure. It's available on [Clojars](http://clojars.com) as a dependency:

https://clojars.org/cartodb-clj

# Usage

Fire up your REPL!

```clojure
(use `cartodb.cient)
(query "vertnet" "SELECT dwca_url FROM publishers LIMIT 3")
[{:dwca_url "http://vertnet.nhm.ku.edu:8080/ipt/archive.do?r=hsu_wildlife_birds"} {:dwca_url "http://vertnet.nhm.ku.edu:8080/ipt/archive.do?r=dmnh_birds"} {:dwca_url "http://vertnet.nhm.ku.edu:8080/ipt/archive.do?r=hsu_wildlife_birds"}]
```

## License

Copyright (C) 2012

Distributed under the Eclipse Public License, the same as Clojure.

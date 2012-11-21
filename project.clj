(defproject google-api "0.2.0"
  :description "Google api library for clojure"
  :url "https://github.com/dcar/google-api"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-http "0.5.6"]
                 [cheshire "4.0.3"]]
  :profiles {:dev {:dependencies [[ring/ring-core "1.1.6"]
                                  [ring/ring-jetty-adapter "1.1.6"]]}})

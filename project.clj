(defproject just-married "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/core.async "0.3.442"]
                 [sqlitejdbc "0.5.6"]
                 [datascript "0.16.1"]
                 [doo "0.1.7"]
                 [re-frisk "0.4.5"]

                 [re-frame "0.9.2"]
                 [reagent "0.6.1"]
                 [ring "1.6.0"]
                 [ring-middleware-format "0.7.2" :exclusions [ring]]
                 [ring.middleware.logger "0.5.0"]
                 [ring/ring-defaults "0.3.0"]
                 [ring/ring-json "0.4.0"]
                 [cljs-ajax "0.5.9"]
                 [secretary "1.2.3"]
                 [org.clojure/clojurescript "1.9.521"]
                 [metosin/ring-http-response "0.8.2"]
                 [bk/ring-gzip "0.2.1"]
                 [clj-jwt "0.1.1"]
                 [compojure "1.6.0"]
                 [environ "1.1.0"]
                 [garden "1.3.2"]
                 [http-kit "2.2.0"]
                 ]

  :plugins [[lein-ring "0.8.13"]
            [environ/environ.lein "0.3.1"]
            [lein-cljsbuild "1.1.4"]
            [lein-garden "0.2.8"]]

  :min-lein-version "2.5.3")

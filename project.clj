(defproject just-married "0.1.0-SNAPSHOT"
  :description "Wedding website"
  :url "https://github.com/AndreaCrotti/just-married"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/core.async "0.3.443"]
                 [sqlitejdbc "0.5.6"]
                 [datascript "0.16.1"]
                 [doo "0.1.7"]
                 [re-frisk "0.4.5"]

                 [re-frame "0.9.4"]
                 [reagent "0.7.0"]
                 [ring "1.6.1"]
                 [ring-middleware-format "0.7.2" :exclusions [ring]]
                 [ring.middleware.logger "0.5.0"]
                 [ring/ring-defaults "0.3.0"]
                 [ring/ring-json "0.4.0"]
                 [cljs-ajax "0.6.0"]
                 [secretary "1.2.3"]
                 [org.clojure/clojurescript "1.9.671"]
                 [metosin/ring-http-response "0.9.0"]
                 [bk/ring-gzip "0.2.1"]
                 [clj-jwt "0.1.1"]
                 [compojure "1.6.0"]
                 [environ "1.1.0"]
                 [garden "1.3.2"]
                 ;; this added just to make garden happy?
                 [prone "1.0.1"]
                 [http-kit "2.2.0"]
                 ;; Clojure(script) wrapper for the Stripe API
                 [racehub/stripe-clj "0.3.5"]
                 [tongue "0.2.2"]
                 [camdez/sendgrid "0.1.0"]
                 [raven-clj "1.5.0"]]

  :plugins [[lein-ring "0.8.13"]
            [environ/environ.lein "0.3.1"]
            [lein-cljsbuild "1.1.4"]
            [lein-garden "0.2.8"]]

  :uberjar-name "just-married.jar"
  :min-lein-version "2.5.3"
  :source-paths ["src/clj" "src/cljc"]
  :test-paths ["test/clj" "test/cljc"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"
                                    "resources/public/css"]

  :figwheel {:css-dirs ["resources/public/css"]
             :open-file-command "lein_opener.sh"
             :server-logfile "log/figwheel.log"}

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet just-married.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

  :ring {:handler just-married.api/app
         :auto-reload? true
         :auto-refresh? true}

  :main just-married.api
  :target-path "target/%s"

  :profiles
  {:production {:env {:production true}}
   :uberjar {:hooks []
             :source-paths ["src/clj" "src/cljc"]
             :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
             :omit-source true
             :aot :all
             :main just-married.api}
   :dev
   {:plugins [[lein-figwheel "0.5.11"]
              [lein-doo "0.1.7"]]

    :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
    :dependencies [[binaryage/devtools "0.9.4"]
                   [com.cemerick/piggieback "0.2.2"]
                   [figwheel "0.5.11"]
                   [figwheel-sidecar "0.5.11"]
                   [javax.servlet/servlet-api "2.5"]
                   [lambdaisland/garden-watcher "0.3.1"]
                   ;; dependencies for the reloaded workflow
                   [ns-tracker "0.3.1"]
                   [reloaded.repl "0.2.3"]
                   [ring-mock "0.1.5"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs" "src/cljc"]
     :figwheel     {:on-jsload "just-married.core/mount-root"}
     :compiler     {:main                 just-married.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs" "src/cljc"]
     :compiler     {:main            just-married.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs" "src/cljc" "test/cljc"]
     :compiler     {:main          just-married.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none}}
    ]}
  )

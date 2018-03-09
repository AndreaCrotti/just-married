(defproject just-married "0.1.0-SNAPSHOT"
  :description "Wedding website"
  :url "https://github.com/AndreaCrotti/just-married"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 ;; ring dependencies
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [compojure "1.6.0"]
                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [camdez/sendgrid "0.1.0"]
                 [raven-clj "1.5.1"]
                 [clj-http "3.7.0"]
                 
                 ;; clojurescript dependencies
                 [org.clojure/clojurescript "1.9.946"]
                 [re-frame "0.10.2"]
                 [day8.re-frame/http-fx "0.1.4"]
                 [cljs-ajax "0.7.3"]
                 ;; also ns-tracker is needed not only in dev
                 [ns-tracker "0.3.1"]
                 [garden "1.3.3"]
                 [tongue "0.2.3"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [com.taoensso/tempura "1.1.2"]
                 [cljs-http "0.1.44"]

                 ;; testing libraries, could they also not be in here at all?
                 [doo "0.1.8"]
                 [day8.re-frame/test "0.1.5"]
                 [day8.re-frame/trace "0.1.13"]
                 [clj-recaptcha "0.0.2"]

                 [environ "1.1.0"]
                 ;; this added just to make garden happy?
                 [prone "1.1.4"]
                 ;; database libraries
                 [nilenso/honeysql-postgres "0.2.3"]
                 [clj-postgresql "0.7.0"]
                 [org.clojure/java.jdbc "0.7.4"]
                 [org.postgresql/postgresql "42.1.4"]

                 [honeysql "0.9.1"]
                 [migratus "1.0.2"]
                 [com.rpl/specter "1.0.5"]
                 [mount "0.1.11"]
                 [buddy "2.0.0"]
                 [buddy/buddy-auth "2.1.0"]
                 [hiccup "1.0.5"]
                 [secretary "1.2.3"]
                 [day8/re-frame-tracer "0.1.1-SNAPSHOT"]
                 [reframe-utils "0.1.4"]
                 [com.cemerick/url "0.1.1"]
                 [ring/ring-json "0.4.0"]
                 [clj-pdf "2.2.30"]
                 [cljsjs/moment "2.17.1-1"]
                 [cljsjs/moment-range "3.0.3-0"]]

  :plugins [[environ/environ.lein "0.3.1"]
            [lein-ring "0.9.7"]
            [lein-cljsbuild "1.1.4"]
            [lein-cljfmt "0.5.7"]
            [lein-garden "0.2.8"]]

  :uberjar-name "just-married.jar"
  :min-lein-version "2.7.1"
  :source-paths ["src/clj" "src/cljc"]
  :test-paths ["test/clj" "test/cljc"]
  :resource-paths ["config" "resources"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"
                                    "test/js"
                                    "resources/public/css"
                                    "out"]

  :figwheel {:css-dirs ["resources/public/css"]
             :open-file-command "lein_opener.sh"
             :ring-handler just-married.api/app
             :server-logfile "log/figwheel.log"
             :server-port 3450
             :server-ip "127.0.0.1"}

  :ring {:handler just-married.api/app}
  :main ^{:skip-aot true} just-married.api
  :target-path "target/%s"

  :doo {:alias {:browsers [:phantomjs]}}

  :migratus {:store :database
             :migration-dir "migrations"
             ;; can use environ here??
             :db ~(get (System/getenv) "DATABASE_URL")}
  :profiles
  {:production {:env {:production true}}
   :uberjar {:hooks []
             :source-paths ["src/clj" "src/cljc"]
             :prep-tasks [["compile"]
                          ["garden" "once"]
                          ["cljsbuild" "once" "min"]]

             :omit-source true
             :aot :all
             :main just-married.api}
   :dev
   {:aliases {"run-dev" ["trampoline" "run" "-m" "just-married.server/run-dev"]}
    :env {:devtools-debug "true"}
    :plugins [[lein-figwheel "0.5.14"]
              [lein-doo "0.1.7"]
              [migratus-lein "0.5.0"]]

    :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
    :dependencies [[binaryage/devtools "0.9.8"]
                   [com.cemerick/piggieback "0.2.2"]
                   [figwheel "0.5.14"]
                   [figwheel-sidecar "0.5.14"]
                   [javax.servlet/servlet-api "2.5"]
                   [lambdaisland/garden-watcher "0.3.2"]
                   ;; dependencies for the reloaded workflow
                   [reloaded.repl "0.2.4"]
                   [ring/ring-mock "0.3.2"]]}}

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj" "src/cljc"]
                     :stylesheet just-married.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}
  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs" "src/cljc"]
     :figwheel     {:on-jsload "just-married.core/mount-root"}
     :compiler     {:main                 just-married.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :optimizations :none
                    :source-map true
                    :source-map-timestamp true
                    :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true}
                    :preloads             [devtools.preload day8.re-frame.trace.preload]
                    :external-config      {:devtools/config {:features-to-install [:formatters
                                                                                   :async
                                                                                   :hints]}}}}

    {:id           "min"
     :source-paths ["src/cljs" "src/cljc"]
     :compiler     {:main            just-married.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :output-dir "resources/public/js/compiled"
                    :source-map "resources/public/js/compiled/app.js.map"
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

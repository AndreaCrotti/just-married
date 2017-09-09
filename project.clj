(defproject just-married "0.1.0-SNAPSHOT"
  :description "Wedding website"
  :url "https://github.com/AndreaCrotti/just-married"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clojure-future-spec "1.9.0-alpha17"]
                 ;; pedestal dependencies
                 [io.pedestal/pedestal.service       "0.5.2"]
                 [io.pedestal/pedestal.service-tools "0.5.2"]
                 [io.pedestal/pedestal.jetty         "0.5.2"]
                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [geheimtur "0.3.3"]

                 [org.slf4j/jul-to-slf4j "1.7.25"]
                 [org.slf4j/jcl-over-slf4j "1.7.25"]
                 [org.slf4j/log4j-over-slf4j "1.7.25"]

                 ;; various integrations
                 [racehub/stripe-clj "0.3.5"]
                 [camdez/sendgrid "0.1.0"]
                 [raven-clj "1.5.0"]
                 [clj-http "3.6.1"]

                 ;; clojurescript dependencies
                 [org.clojure/clojurescript "1.9.854"]
                 [re-frisk "0.4.5"]
                 [re-frame "0.9.4"]
                 [cljs-ajax "0.6.0"]
                 ;; also ns-tracker is needed not only in dev
                 [ns-tracker "0.3.1"]
                 [garden "1.3.2"]
                 [tongue "0.2.2"]
                 [com.andrewmcveigh/cljs-time "0.5.0"]
                 [com.taoensso/tempura "1.1.2"]
                 [cljs-http "0.1.43"]

                 ;; testing libraries, could they also not be in here at all?
                 [doo "0.1.7"]
                 [day8.re-frame/test "0.1.5"]
                 [clj-recaptcha "0.0.2"]

                 [environ "1.1.0"]
                 ;; this added just to make garden happy?
                 [prone "1.1.4"]
                 ;; database libraries
                 [nilenso/honeysql-postgres "0.2.3"]
                 [clj-postgresql "0.7.0"]
                 [org.clojure/java.jdbc "0.7.0"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]

                 [honeysql "0.9.0"]
                 [migratus "0.9.8"]
                 [com.rpl/specter "1.0.3"]]

  :plugins [[environ/environ.lein "0.3.1"]
            [lein-cljsbuild "1.1.4"]
            [lein-garden "0.2.8"]]

  :uberjar-name "just-married.jar"
  :min-lein-version "2.7.1"
  :source-paths ["src/clj" "src/cljc"]
  :test-paths ["test/clj" "test/cljc"]
  :resource-paths ["config" "resources"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"
                                    "resources/public/css"]

  :figwheel {:css-dirs ["resources/public/css"]
             :open-file-command "lein_opener.sh"
             :server-logfile "log/figwheel.log"}


  :main ^{:skip-aot true} just-married.server
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
             :main just-married.server}
   :dev
   {:aliases {"run-dev" ["trampoline" "run" "-m" "just-married.server/run-dev"]}
    :plugins [[lein-figwheel "0.5.12"]
              [lein-doo "0.1.7"]
              [migratus-lein "0.5.0"]]

    :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
    :dependencies [[binaryage/devtools "0.9.4"]
                   [com.cemerick/piggieback "0.2.2"]
                   [io.pedestal/pedestal.service-tools "0.5.2"]
                   [figwheel "0.5.12"]
                   [figwheel-sidecar "0.5.12"]
                   [javax.servlet/servlet-api "2.5"]
                   [lambdaisland/garden-watcher "0.3.1"]
                   ;; dependencies for the reloaded workflow
                   [reloaded.repl "0.2.3"]]}}


  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
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
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

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

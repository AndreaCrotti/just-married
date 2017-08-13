(defproject just-married "0.1.0-SNAPSHOT"
  :description "Wedding website"
  :url "https://github.com/AndreaCrotti/just-married"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.443"]
                 [org.clojure/java.jdbc "0.7.0"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]

                 [honeysql "0.9.0"]
                 [migratus "0.9.8"]

                 [doo "0.1.7"]
                 [re-frisk "0.4.5"]

                 [re-frame "0.9.4"]
                 [reagent "0.7.0"]
                 [cljs-ajax "0.6.0"]
                 [org.clojure/clojurescript "1.9.854"]
                 [environ "1.1.0"]
                 [garden "1.3.2"]
                 ;; this added just to make garden happy?
                 [prone "1.1.4"]
                 [http-kit "2.2.0"]
                 ;; Clojure(script) wrapper for the Stripe API
                 [racehub/stripe-clj "0.3.5"]
                 [tongue "0.2.2"]
                 [camdez/sendgrid "0.1.0"]
                 [raven-clj "1.5.0"]
                 [clj-http-fake "1.0.3"]
                 ;; pedestal dependencies
                 [io.pedestal/pedestal.service       "0.5.2"]
                 [io.pedestal/pedestal.service-tools "0.5.2"] ;; Only needed for ns-watching; WAR tooling
                 [io.pedestal/pedestal.jetty         "0.5.2"]
                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]

                 [org.slf4j/jul-to-slf4j "1.7.25"]
                 [org.slf4j/jcl-over-slf4j "1.7.25"]
                 [org.slf4j/log4j-over-slf4j "1.7.25"]
                 ;; date manipulation in clojurescript
                 [com.andrewmcveigh/cljs-time "0.5.0"]
                 ;; XXX: should move this up as well but for some crazy
                 ;; reason it breaks piggieback if I do so!
                 [nilenso/honeysql-postgres "0.2.3"]
                 [clj-postgresql "0.7.0"]
                 ;; translation library
                 [com.taoensso/tempura "1.1.2"]
                 ;; authentication with pedestal
                 [geheimtur "0.3.3"]]

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

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet just-married.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

  :main ^{:skip-aot true} just-married.server
  :target-path "target/%s"

  :doo {:alias {:browsers [:phantomjs]}}

  :migratus {:store :database
             :migration-dir "migrations"
             :db ~(get (System/getenv) "DATABASE_URL")}
  :profiles
  {:production {:env {:production true}}
   :uberjar {:hooks []
             :source-paths ["src/clj" "src/cljc"]
             :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
             :omit-source true
             :aot :all
             :main just-married.api}
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
                   [ns-tracker "0.3.1"]
                   [reloaded.repl "0.2.3"]]}}


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

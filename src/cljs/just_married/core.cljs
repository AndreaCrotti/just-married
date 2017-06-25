(ns just-married.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              ;; how do I make it happen only on dev?
              [re-frisk.core :refer [enable-re-frisk!]]
              [just-married.events]
              [just-married.subs]
              [just-married.views :as views]))

(def debug?
  ^boolean js/goog.DEBUG)

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (enable-re-frisk!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))

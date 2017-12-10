(ns just-married.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frisk.core :refer [enable-re-frisk!]]
            [just-married.events]
            [just-married.subs]
            [just-married.views :as views]))

(def debug?
  ^boolean js/goog.DEBUG)

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (enable-re-frisk!)))

(defn mount-root [page]
  (re-frame/clear-subscription-cache!)
  (reagent/render [page]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root views/main-panel))

(defn ^:export init-guests []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root views/guests))

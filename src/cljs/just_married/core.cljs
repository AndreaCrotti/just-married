(ns just-married.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [just-married.home.handlers]
            [just-married.home.views :as home-views]))

(def debug?
  ^boolean js/goog.DEBUG)

(defn dev-setup []
  (when debug?
    (enable-console-print!)))

(defn mount-root [page]
  (re-frame/clear-subscription-cache!)
  (reagent/render [page]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root home-views/main-panel))

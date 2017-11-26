(ns just-married.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            ;; how do I make it happen only on dev?
            [secretary.core :as secretary :refer-macros [defroute]]
            [re-frisk.core :refer [enable-re-frisk!]]
            [just-married.events]
            [just-married.subs]
            [just-married.views :as views]))

(defn page []
  #_(if-let [current-page
           @(re-frame/subscribe [:current-page])])
  views/main-panel)

(defroute "/" []
  (re-frame/dispatch-sync [:set-current-page views/main-panel]))

(defroute "/guests" []
  (re-frame/dispatch-sync [:set-current-page views/guests]))

(def debug?
  ^boolean js/goog.DEBUG)

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (enable-re-frisk!)))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (js/console.log "page is now " (page))
  (reagent/render [#'page]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))

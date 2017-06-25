(ns just-married.views
  (:require [re-frame.core :as re-frame :refer [dispatch subscribe]]))

(defn main-panel
  []
  (fn []
    [:g "hello world"]))

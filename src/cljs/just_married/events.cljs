(ns just-married.events
  (:require [re-frame.core :as re-frame :refer [dispatch reg-event-db]]))


(reg-event-db
 :set-language
 (fn [db [_ language]]
   (assoc db :language language)))

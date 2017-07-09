(ns just-married.events
  (:require [re-frame.core :as re-frame :refer [dispatch reg-event-db]]
            [just-married.db :as db]))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(reg-event-db
 :set-language
 (fn [db [_ language]]
   (assoc db :language language)))

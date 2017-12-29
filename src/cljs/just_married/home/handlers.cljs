(ns just-married.home.handlers
  (:require [re-frame.core :as re-frame :refer [reg-sub dispatch reg-event-db]]
            [just-married.home.db :as db]))

(reg-sub
 :expanded-story
 :expanded-story)

(reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(reg-event-db
 :set-expanded-story
 (fn [db [_ expanded-story]]
   (assoc db :expanded-story expanded-story)))

(ns just-married.home.events
  (:require [re-frame.core :as re-frame :refer [dispatch reg-event-db]]
            [just-married.home.db :as db]
            [just-married.home.language :refer [available-languages]]
            [ajax.core :refer [GET]]))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(reg-event-db
 :set-language
 (fn [db [_ language]]
   (assoc db :language language)))

(reg-event-db
 :set-expanded-story
 (fn [db [_ expanded-story]]
   (assoc db :expanded-story expanded-story)))

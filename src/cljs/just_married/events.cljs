(ns just-married.events
  (:require [re-frame.core :as re-frame :refer [dispatch reg-event-db]]
            [just-married.db :as db]
            [just-married.language :refer [AVAILABLE-LANGUAGES]]
            [ajax.core :refer [GET]]))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   (let [parsed-language (keyword js/window.detected_language)
         language (get (set AVAILABLE-LANGUAGES) parsed-language :en)]
     (assoc db/default-db :language language))))

(reg-event-db
 :set-language
 (fn [db [_ language]]
   (assoc db :language language)))

(reg-event-db
 :name
 (fn [db [_ name]]
   (assoc db :name name)))

(reg-event-db
 :email
 (fn [db [_ name]]
   (assoc db :email name)))

(defn confirm
  [coming db]
  (print "Calling function to record coming"))

(reg-event-db
 :coming
 (fn [db [_]]
   (confirm db true)))

(reg-event-db
 :not-coming
 (fn [db [_]]
   (confirm db false)))

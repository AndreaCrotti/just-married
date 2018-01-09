(ns just-married.home.handlers
  (:require [re-frame.core :as re-frame :refer [reg-sub dispatch reg-event-db reg-event-fx]]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [just-married.home.db :as db]))

(defn- getter
  [key]
  (fn [db _]
    (key db)))

(defn- setter
  [key]
  (fn [db [_ val]]
    (assoc db key val)))

(defn- switch-on
  [key]
  (fn [db _]
    (assoc db key true)))

(reg-sub
 :expanded-story
 (getter :expanded-story))

(reg-sub
 :name
 (getter :name))

(reg-sub
 :email
 (getter :email))

(reg-sub
 :dietary
 (getter :dietary))

(reg-event-db
 :set-name
 (setter :name))

(reg-event-db
 :set-email
 (setter :email))

(reg-event-db
 :set-comment
 (setter :comments))

(reg-sub
 :show-confirmation-success
 (getter :show-confirmation-success))

(reg-sub
 :show-confirmation-failure
 (getter :show-confirmation-failure))

(defn notify
  [{:keys [db]} [_ value]]
  {:db db
   :http-xhrio {:method :post
                :uri "/notify"
                :params {:coming value
                         :name (:name db)
                         :email (:email db)
                         :comments (:comments db)}
                :format (ajax/json-request-format)
                :response-format (ajax/json-response-format
                                  {:keywords? true})
                :on-success [:confirmation-sent]
                :on-failure [:confirmation-not-sent]}})

(reg-event-fx
 :send-notification
 notify)

(reg-sub
 :confirmation-sent
 (switch-on :show-confirmation-success))

(reg-event-db
 :confirmation-not-sent
 (switch-on :show-confirmation-failure))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(reg-event-db
 :set-expanded-story
 (fn [db [_ expanded-story]]
   (assoc db :expanded-story expanded-story)))

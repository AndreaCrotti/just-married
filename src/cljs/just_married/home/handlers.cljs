(ns just-married.home.handlers
  (:require [re-frame.core :as re-frame :refer [reg-sub dispatch reg-event-db reg-event-fx]]
            [ajax.core :as ajax]
            [just-married.home.config :as config]
            [day8.re-frame.http-fx]))

(def default-db
  ;; what other possibly useful information could be here?
  {:language              :en
   :expanded-story        false
   :show-confirmation-msg false
   :rvsp                  {:name     nil
                           :email    nil
                           :how-many config/default-how-many
                           :comment  nil}})

(defn- getter
  [key]
  (fn [db _]
    (key db)))

(defn- setter
  [key]
  (fn [db [_ val]]
    (assoc-in db key val)))

(reg-sub
 :expanded-story
 (getter :expanded-story))

(reg-sub
 :name
 (getter :name))

(reg-sub
 :email
 (getter :email))

(reg-event-db
 :set-name
 (setter [:rvsp :name]))

(reg-event-db
 :set-email
 (setter [:rvsp :email]))

(reg-event-db
 :set-how-many
 (setter [:rvsp :how-many]))

(reg-event-db
 :set-comment
 (setter [:rvsp :comment]))

(reg-sub
 :show-confirmation-success
 (getter :show-confirmation-success))

(reg-sub
 :show-confirmation-failure
 (getter :show-confirmation-failure))

(defn rvsp
  [{:keys [db]} [_ value]]
  {:db         db
   :http-xhrio {:method          :post
                :uri             "/api/rvsp"
                :params          (assoc (:rvsp db) :coming value)
                :format          (ajax/json-request-format)
                :response-format (ajax/json-response-format
                                  {:keywords? true})
                :on-success      [:confirmation-sent]
                :on-failure      [:confirmation-not-sent]}})

(reg-event-fx
 :send-notification
 rvsp)

(reg-sub
 :show-confirmation-msg
 (getter :show-confirmation-msg))

(reg-event-db
 :confirmation-sent
 (fn [db _]
   (assoc-in db [:show-confirmation-msg] true)))

(reg-event-db
 :confirmation-not-sent
 (fn [db _]
   ;; should actually handle the error and/or log
   ;; it properly somewhere
   (assoc-in db [:show-confirmation-msg] true)))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   default-db))

(reg-event-db
 :set-expanded-story
 (fn [db [_ expanded-story]]
   (assoc db :expanded-story expanded-story)))

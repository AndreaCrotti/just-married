(ns just-married.home.handlers
  (:require [re-frame.core :as re-frame :refer [reg-sub dispatch reg-event-db reg-event-fx]]
            [ajax.core :as ajax]
            [just-married.home.config :as config]
            [day8.re-frame.http-fx]))

(def default-db
  ;; what other possibly useful information could be here?
  {:language       :en
   :expanded-story false
   :rvsp           {:name     nil
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

;; register simple setters for all the rvsp fields dynamically
(doseq [field (-> default-db :rvsp keys)]
  (reg-event-db
   (keyword (str "set-" (name field)))
   (setter [:rvsp field])))

(defn rvsp
  [{:keys [db]} [_ value]]
  {:db         db
   :http-xhrio {:method          :post
                :uri             "/api/rvsp"
                :params          (assoc (:rvsp db) :coming value)
                ;; could use EDN as well here potentially
                :format          (ajax/json-request-format)
                :response-format (ajax/json-response-format {:keywords? true})
                :on-success      [:confirmation-sent]
                :on-failure      [:confirmation-not-sent]}})

(reg-event-fx
 :send-notification
 rvsp)

(defn handle
  [response]
  (case (:status response)
    201 (js/alert "Thanks for letting us know")
    400 (js/alert "Missing required field `Name`")
    (js/alert "Could not submit your response")))

;;XXX: getting strangely cjls-ajax to report failure
;;even if in fact it worked and it was a 201 response
(reg-event-db
 :confirmation-sent
 (fn [db [_ response]]
   (handle response)
   db))

(reg-event-db
 :confirmation-not-sent
 (fn [db [_ response]]
   (handle response)
   db))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   default-db))

(reg-event-db
 :set-expanded-story
 (fn [db [_ expanded-story]]
   (assoc db :expanded-story expanded-story)))

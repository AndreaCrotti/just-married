(ns just-married.home.handlers
  (:require [re-frame.core :as re-frame :refer [reg-sub dispatch reg-event-db reg-event-fx]]
            [ajax.core :as ajax]
            [just-married.home.config :as config]
            [just-married.home.language :refer [translate]]
            [day8.re-frame.http-fx]))

(def ^:private handlers-dict
  {:en {:worked "Thanks for letting us know"
        :missing "Missing required field `Name`"
        :not-working "Could not submit your response"}

   :it {:worked "Grazie per averci fatto sapere"
        :missing "Il campo `Nome` è richiesto"
        :not-working "Errore nel sottomettere il tuo form"}})

(def ^:private tr (partial translate handlers-dict))

(def default-db
  ;; what other possibly useful information could be here?
  {:language       :en
   :expanded-story false
   :rvsp           {:name      nil
                    :email     nil
                    :how-many  config/default-how-many
                    :comment   nil
                    :is-coming nil}})

(defn- getter
  [path]
  (fn [db _]
    (get-in db path)))

(defn- setter
  [path]
  (fn [db [_ val]]
    (assoc-in db path val)))

(reg-sub
 :expanded-story
 (getter [:expanded-story]))

(reg-sub
 :is-coming
 (getter [:rvsp :is-coming]))

;; register simple setters for all the rvsp fields dynamically
(doseq [field (-> default-db :rvsp keys)]
  (reg-event-db
   (keyword (str "set-" (name field)))
   (setter [:rvsp field])))

(defn rvsp
  [{:keys [db]}]
  {:db         db
   :http-xhrio {:method          :post
                :uri             "/api/rvsp"
                :params          (:rvsp db)
                ;; could use EDN as well here potentially
                :format          (ajax/json-request-format)
                :response-format (ajax/json-response-format {:keywords? true})
                :on-success      [:confirmation-sent]
                :on-failure      [:confirmation-not-sent]}})

(defn handle
  [response]
  (case (:status response)
    201 (js/alert (tr :worked))
    400 (js/alert (tr :missing))
    (js/alert (tr :not-working))))

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

(reg-event-fx
 :send-notification
 rvsp)

(reg-event-db
 :initialize-db
 (fn  [_ _]
   default-db))

(reg-event-db
 :set-expanded-story
 (fn [db [_ expanded-story]]
   (assoc db :expanded-story expanded-story)))

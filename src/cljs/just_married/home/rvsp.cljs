(ns just-married.home.rvsp
  (:require [just-married.home.language :refer [translate]]
            [just-married.home.config :as config]
            [re-frame.core :refer [dispatch subscribe]]))

(def ^:private rvsp-dict
  {:en {:rvsp "RVSP"
        :email "Email"
        :email-sample "pippo@disneyland.com"
        :name-sample "Pippo"
        :name "Name"
        :coming "I'll be there"
        :comment-sample"Eg My girflriend is celiac, I can't eat peanuts"
        :comment "Other comments (any allergies/special requirements)"
        :not-coming "Sorry I can't"
        :confirmation-sent "Thanks for letting us know!"
        :how-many "How many are you?"}

   :it {:rvsp "RVSP"
        :email "Email"
        :email-sample "pippo@disneyland.com"
        :name "Nome"
        :name-sample "Pippo"
        :coming "Contaci"
        :comment "Altri commenti (allergie/esigenze particolari)"
        :comment-sample "Per esempio la mia ragazza e' ciliaca, io posso mangiare arachidi"
        :not-coming "Non posso"
        :confirmation-sent "Grazie per averci fatto sapere!"
        :how-many "Quanti siete?"}})

(def ^:private tr (partial translate rvsp-dict))

(defn- set-val
  [handler-key]
  #(dispatch [handler-key (-> % .-target .-value)]))

(defn rvsp
  []
  (let [show-confirmation-msg (subscribe [:show-confirmation-msg])]
    (fn []
      [:div.rvsp {:id "rvsp"}
       [:h3 (tr :rvsp)]
       
       [:div.rvsp__form
        [:label (tr :name)]
        [:input.rvsp__name {:type        "text"
                            :placeholder (tr :name-sample)
                            :on-change   (set-val :set-name)}]

        [:label (tr :email)]
        [:input.rvsp__email {:type        "email"
                             :placeholder (tr :email-sample)
                             :on-change   (set-val :set-email)}]

        [:label (tr :how-many)]
        (into [:select.rvsp__howmany {:on-change (set-val :set-how-many)}]
              (for [n (range 1 10)]
                (let [v (if (= n config/default-how-many)
                          {:value n :selected "selected"}
                          {:value n})]
                  [:option v n])))

        [:label (tr :comment)]
        [:textarea.rvsp__comment {:rows        2
                                  :cols        100
                                  :placeholder (tr :comment-sample)
                                  :on-change   (set-val :set-comment)}]
        
        [:button.rvsp__confirm.rvsp__coming
         {:on-click #(dispatch [:send-notification true])} (tr :coming)]

        [:button.rvsp__confirm.rvsp__not_coming
         {:on-click #(dispatch [:send-notification false])} (tr :not-coming)]

        #_(when @show-confirmation-msg
            [:div.confirmation__display (tr :confirmation-sent)])]])))


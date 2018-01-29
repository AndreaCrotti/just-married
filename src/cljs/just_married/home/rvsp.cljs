(ns just-married.home.rvsp
  (:require [just-married.home.language :refer [translate]]
            [re-frame.core :refer [dispatch subscribe]]))

(def ^:private rvsp-dict
  {:en {:rvsp "RVSP"
        :email "Email"
        :email-sample "pippo@disneyland.com"
        :name-sample "Pippo"
        :name "Name"
        :coming "I'll be there"
        :comments-sample"Eg My girflriend is celiac, I can't eat peanuts"
        :comments "Other comments (any allergies/special requirements)"
        :not-coming "Sorry I can't"
        :confirmation-sent "Thanks for letting us know!"
        :how-many "How many are you?"}

   :it {:rvsp "RVSP"
        :email "Email"
        :email-sample "pippo@disneyland.com"
        :name "Nome"
        :name-sample "Pippo"
        :coming "Contaci"
        :comments "Altri commenti (allergie/esigenze particolari)"
        :comments-sample "Per esempio la mia ragazza e' ciliaca, io posso mangiare arachidi"
        :not-coming "Non posso"
        :confirmation-sent "Grazie per averci fatto sapere!"
        :how-many "Quanti siete?"}})

(def ^:private tr (partial translate rvsp-dict))

(defn- set-val
  [handler-key]
  #(dispatch [handler-key (-> % .-target .-value)]))

(defn rvsp
  []
  (let [confirmation-sent (subscribe [:confirmation-sent])
        ;; confirmation-not-sent (subscribe [:confirmation-not-sent])
        ]

    [:div.rvsp {:id "rvsp"}
     [:h3 (tr :rvsp)]
     
     [:div.rvsp__form
      [:label (tr :name)]
      [:input.rvsp__name {:type "text"
                          :placeholder (tr :name-sample)
                          :on-change (set-val :set-name)}]

      [:label (tr :email)]
      [:input.rvsp__email {:type "email"
                           :placeholder (tr :email-sample)
                           :on-change (set-val :set-email)}]

      [:label (tr :how-many)]
      (into [:select.rvsp__howmany]
            (for [n (range 1 10)]
              [:option {:value n
                        :default 2}
               n]))

      [:label (tr :comments)]
      [:textarea.rvsp__comments {:rows 2
                                 :cols 100
                                 :placeholder (tr :comments-sample)
                                 :on-change (set-val :set-comment)}]
      
      [:button.rvsp__confirm.rvsp__coming
       {:on-click #(dispatch [:send-notification true])} (tr :coming)]

      [:button.rvsp__confirm.rvsp__not_coming
       {:on-click #(dispatch [:send-notification false])} (tr :not-coming)]]

     #_(when @confirmation-sent
       [:div.confirmation__display (tr :confirmation-sent)])]))

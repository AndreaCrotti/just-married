(ns just-married.home.rvsp
  (:require [just-married.home.language :refer [translate]]
            [just-married.home.config :as config]
            [re-frame.core :refer [dispatch subscribe]]))

(def ^:private rvsp-dict
  {:en {:rvsp "RVSP"
        :email "Email"
        :email-sample "john@smith.com"
        :name-sample "John Smith"
        :name "Name (*)"
        :coming "I'll be there"
        :comment-sample"Eg My girflriend is celiac, I can't eat peanuts"
        :comment "Other comments (any allergies/special requirements)"
        :not-coming "Sorry I can't"
        :confirmation-sent "Thanks for letting us know!"
        :how-many "How many are you?"
        :submit "Let us know"}

   :it {:rvsp "RVSP"
        :email "Email"
        :email-sample "mario@rossi.com"
        :name "Nome (*)"
        :name-sample "Mario Rossi"
        :coming "Contaci"
        :comment "Altri commenti (allergie/esigenze particolari)"
        :comment-sample "Per esempio la mia ragazza e' ciliaca, io posso mangiare arachidi"
        :not-coming "Non posso"
        :confirmation-sent "Grazie per averci fatto sapere!"
        :how-many "Quanti siete?"
        :submit "Facci sapere"}})

(def ^:private tr (partial translate rvsp-dict))

(defn- set-val
  [handler-key]
  #(dispatch [handler-key (-> % .-target .-value)]))

(defn form-fields
  [with-details]
  (let [basic-fields
        [[:label (tr :name)]
         [:input.rvsp__name {:type        "text"
                             :placeholder (tr :name-sample)
                             :on-change   (set-val :set-name)}]

         [:button.rvsp__confirm.rvsp__coming
          {:on-click #(dispatch [:set-is-coming true])} (tr :coming)]

         [:button.rvsp__confirm.rvsp__not_coming
          {:on-click #(dispatch [:set-is-coming false])} (tr :not-coming)]]

        detail-fields
        [[:label (tr :email)]
         [:input.rvsp__email {:type        "email"
                              :placeholder (tr :email-sample)
                              :on-change   (set-val :set-email)}]

         [:label (tr :comment)]
         [:textarea.rvsp__comment {:rows        2
                                   :cols        100
                                   :placeholder (tr :comment-sample)
                                   :on-change   (set-val :set-comment)}]

         [:label (tr :how-many)]
         (into [:select.rvsp__howmany {:on-change (set-val :set-how-many)
                                       :value     config/default-how-many}]
               (for [n (range 1 10)]
                 [:option {:value n} n]))]

        fields (if with-details (concat basic-fields detail-fields) basic-fields)
        confirm-button
        [:button.rvsp__submit {:on-click #(dispatch [:send-notification])}
         (tr :submit)]]

    (concat fields [confirm-button])))

(defn rvsp
  []
  (let [is-coming (subscribe [:is-coming])]
    (fn []
      [:div.rvsp {:id "rvsp"}
       [:h3 (tr :rvsp)]

       (into [:div.rvsp__form]
             (form-fields @is-coming))])))

(ns just-married.home.rvsp
  (:require [just-married.home.language :refer [translate]]
            [re-frame.core :refer [dispatch subscribe]]))

(def ^:private rvsp-dict
  {:en {:rvsp "RVSP"
        :email "Email"
        :name "Name"
        :coming "I'll be there"
        :comments "Other comments (allergies)"
        :not-coming "Sorry I can't"
        :confirmation-sent "Thanks for letting us know!"}

   :it {:rvsp "RVSP"
        :email "Email"
        :name "Nome"
        :coming "Contaci"
        :comments "Altri commenti (allergie)"
        :not-coming "Non posso"
        :confirmation-sent "Grazie per averci fatto sapere!"}})

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
     ;; should disappear in a short time??
     (when @confirmation-sent
       [:div.confirmation__display (tr :confirmation-sent)])

     [:div.rvsp__form
      [:ul
       [:li
        [:input.rvsp__name {:type "text"
                            :placeholder (tr :name)
                            :on-change (set-val :set-name)}]]

       [:li
        [:input.rvsp__email {:type "email"
                             :placeholder (tr :email)
                             :on-change (set-val :set-email)}]]

       [:li
        [:input.rvsp__comments {:type "textarea"
                                :placeholder (tr :comments)
                                :on-change (set-val :set-comment)}]]]
      
      [:button.rvsp__confirm.rvsp__coming
       {:on-click #(dispatch [:send-notification true])} (tr :coming)]

      [:button.rvsp__confirm.rvsp__not_coming
       {:on-click #(dispatch [:send-notification false])} (tr :not-coming)]]]))

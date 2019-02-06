(ns just-married.home.social)

(defn social
  []
  [:div.social.section {:id "social"}
   [:h3 "Foto"]
   [:div.social__content
    [:span "Condividete le vostre foto e i commenti con noi a "]
    [:a {:href   "https://photos.app.goo.gl/P96B9LjWb5vns5ku7"
         :target "_blank"}

     "questo link"]

    [:span ", ci divertiremo insieme!"]]])

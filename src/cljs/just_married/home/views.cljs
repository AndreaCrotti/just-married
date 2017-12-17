(ns just-married.home.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [just-married.home.language :refer [lang-selection translate]]
   [just-married.home.payment-views :as payment-views]
   [just-married.home.countdown :as countdown]
   [just-married.home.settings :as settings]
   [just-married.home.story :refer [STORY-TEXT]]))

;; should dispatch the right language also here of course
(defn add-to-calendar
  [language]
  [:div {:id "add-to-calendar"}
   [:a {:target "_blank"
        :href (get settings/WEDDING-DAY language)}

    ;; actually use current language
    (translate language :add-to-calendar)
    [:img {:src settings/GOOGLE-CALENDAR-IMG}]]])

;;TODO: evaluate using a macro to avoid redefining the same king
;;of function every time

(defn timeline
  []
  (let [language (subscribe [:current-language])]
    ))

(defn countdown
  []
  (let [language (subscribe [:current-language])]
    (fn []
      [:div {:id "countdown" :class "section"}
       [:div {:class "names"} "Andrea Crotti & Enrica Verrucci"]
       [:div {:class "find-us"}
        [:a {:href "#find-us"} (-> settings/PLACES :wedding :name)]]

       [:div {:class "date"} (translate @language :date)]
       [:div {:id "countdown"}
        (countdown/countdown-component @language)]

       (add-to-calendar @language)])))

(defn story
  []
  (let [language (subscribe [:current-language])]
    (fn []
      [:div {:id "story" :class "section"}
       [:blockquote
        (get STORY-TEXT @language "Not found")]])))

(defn find-us
  []
  (let [language (subscribe [:current-language])]
    [:div {:id "find-us" :class "section"}
     [:p {:id "find-us-text"}
      (translate @language :find-us-text)]
     [:div {:id "map"}]]))

(defn gifts
  []
  [:div {:id "gifts" :class "section"}
   [:div {:id "amazon-wish-list"}
    [:a {:href settings/AMAZON-WISH-LIST} "Amazon wish list"]]])

(defn rvsp
  []
  [:div {:id "rvsp" :class "section"}
   [:input {:type "text"
            :placeholder "Your name"
            :class "form-control"
            :on-change #(dispatch [:name (-> % .-target .-value)])}]

   [:input {:type "email"
            :placeholder "Your email address"
            :class "form-control"
            :on-change #(dispatch [:email (-> % .-target .-value)])}]

   ;; add something about dietary requirements here if possible
   [:button {:id "confirm-coming"
             :class "btn btn-success btn-medium"
             :on-click #(dispatch [:coming])}
    "Pleasure to join you!"]

   [:button {:id "confirm-not-coming"
             :class "btn btn-danger btn-medium"
             :on-click #(dispatch [:not-coming])}
    "Sadly can't join you!"]

   #_[:div {:class "g-recaptcha"
          :data-sitekey settings/RECAPTCHA-KEY}]])

(defn contacts
  []
  (let [language (subscribe [:current-language])]
    #_[:div {:class "email"} "Email Address"]))

(def SECTIONS
  {:countdown countdown
   :story story
   :find-us find-us
   ;; :rvsp rvsp
   ;; :gifts gifts
   ;;:accomodation accommodation
   :contacts contacts
   })

;; this is quite bootstrap specific in a way
;; would be good to extract even further
(defn navbar
  []
  (let [language (subscribe [:current-language])
        tr (fn [s] (translate @language s))]

    (fn []
      [:nav {:class "navbar navbar-dark bg-primary"}
       [:div {:class "container-fluid"}
        [:div {:class "navbar-header"}
         [:a {:class "navbar-brand" :href "#"} "Home" ]]

        (into
         [:ul {:class "nav navbar-nav"}]
         (for [sec (keys SECTIONS)]
           [:li {:key (name sec)}
            [:a {:href (str "#" (name sec))} (tr sec)]]))
        
        (into
         [:ul {:class "nav navbar-right" :key "language"}]
         (lang-selection @language))]])))

(defn main-panel
  []
  (fn []
    ;;TODO: there could be a better way to do this
    (into [:g]
          (map vector
               (concat [navbar] (vals SECTIONS))))))


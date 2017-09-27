(ns just-married.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [just-married.language :refer [lang-selection translate]]
   [just-married.payment-views :as payment-views]
   [just-married.countdown :as countdown]
   [just-married.settings :as settings]))

(def SECTIONS-SIMPLE
  [:story
   :find-us
   :rvsp
   :accomodation
   :contacts])

(defn get-browser-language
  "Return the language set in the browser, assuming that
  the browser is actually setting correctly navigator.language.
  Another way could be to use the geo-location potentially"
  []
  (let [browser-lang js/navigator.language]
    (if (clojure.string/starts-with? browser-lang "it")
      :italian
      :english)))

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
         (for [sec SECTIONS-SIMPLE]
           [:li {:key (name sec)}
            [:a {:href (str "#" (name sec))} (tr sec)]]))

        [:ul {:class "nav navbar-right"}
         (lang-selection @language)]]])))

;; should dispatch the right language also here of course
(defn add-to-calendar
  [language]
  [:div {:id "add-to-calendar"}
   [:a {:target "_blank"
        :href (get settings/WEDDING-DAY language)}

    ;; actually use current language
    (translate language :add-to-calendar)
    [:img {:src settings/GOOGLE-CALENDAR-IMG}]]])

(defn story
  []
  (let [language (subscribe [:current-language])]
    (fn []
      [:div {:id "story" :class "section"}
       [:div {:class "names"} "Andrea Crotti & Enrica Verrucci"]
       [:div {:class "find-us"}
        [:a {:href "#find-us"} (-> settings/PLACES :parco :name)]]

       [:div {:class "date"} (translate @language :date)]
       [:div {:id "countdown"}
        (countdown/countdown-component @language)]

       (add-to-calendar @language)])))

(defn find-us
  []
  [:div {:id "find-us" :class "section"}
   [:div {:id "map"}]])

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

;; devtools does not seem to be set up correctly
;; since it doesn't find hints.js for example
;; (log "hello")

(defn main-panel
  []
  (fn []
    [:g
     [navbar]
     ;; lang selection could be moved into the header potentially?
     [story]
     [find-us]
     [gifts]
     [rvsp]]))

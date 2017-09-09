(ns just-married.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [just-married.language :refer [lang-selection]]
   [just-married.payment-views :as payment-views]
   [just-married.countdown :as countdown]
   [just-married.settings :as settings]))

(def SECTIONS
  [["story" "Our Story"]
   ["find-us" "Find us"]
   ["rvsp" "RVSP"]
   ;; ["share" "Share Your Memories"]
   ["accomodation" "Accomodation"]
   ["contacts" "Contacts"]])

;; this is quite bootstrap specific in a way
;; would be good to extract even further
(defn navbar
  []
  (let [current-language (subscribe [:current-language])]
    (fn []
      [:nav {:class "navbar navbar-dark bg-primary"}
       [:div {:class "container-fluid"}
        [:div {:class "navbar-header"}
         [:a {:class "navbar-brand" :href "#"} "Home" ]]

        (into
         [:ul {:class "nav navbar-nav"}]
         (for [s SECTIONS]
           [:li [:a {:href (str "#" (first s))} (second s)]]))

        [:ul {:class "nav navbar-right"}
         [:div {:class "bfh-selectbox bfh-languages"
                :data-language "en_GB"
                :data-available "en_GB,it_IT"
                :data-flags true}
          [:input {:type "hidden" :value ""}]]]]])))


(defn story
  []
  [:div {:id "story" :class "section"}
   [:div {:class "names"} "Andrea Crotti & Enrica Verrucci"]
   [:div {:class "find-us"}
    [:a {:href "#find-us"} (-> settings/PLACES :parco :name)]]

   [:div {:class "date"} "27th May, 2018"]
   [:div {:id "countdown"} (countdown/countdown-component)]])

(defn find-us
  []
  [:div {:id "find-us" :class "section"}
   [:div
    [:a
     {:href (-> settings/PLACES :parco :url)}
     (-> settings/PLACES :parco :name)]]

   [:div {:id "map"} "Map here"]])

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

;; should dispatch the right language also here of course
(defn add-to-calendar
  []
  (let [current-language (subscribe [:current-language])]
    (js/console.log "current language is now " current-language)
    (fn []
      [:div {:id "add-to-calendar"}
       [:a {:target "_blank"
            :href (get settings/WEDDING-DAY :en)}

        ;; actually use current language
        (condp = :en
          :en "Add to Calendar"
          :it "Aggiungi al calendario")

        [:img {:src settings/GOOGLE-CALENDAR-IMG}]]])))

(defn main-panel
  []
  (fn []
    [:g
     [navbar]
     [lang-selection]
     [add-to-calendar]
     ;; lang selection could be moved into the header potentially?
     [story]
     [find-us]
     [gifts]
     [rvsp]]))

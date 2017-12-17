(ns just-married.home.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [just-married.home.language :refer [lang-selection translate]]
   [just-married.home.payment-views :as payment-views]
   [just-married.home.countdown :as countdown]
   [just-married.home.settings :as settings]
   [just-married.home.story :refer [STORY-TEXT]]))

(def ^:private max-story-length 300)

;; should dispatch the right language also here of course
(defn add-to-calendar
  [language]
  [:div.add-to-calendar
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
      [:div.countdown.section
       [:div.names "Andrea Crotti & Enrica Verrucci"]
       [:div.find-us
        [:a {:href "#find-us"} (-> settings/PLACES :wedding :name)]]

       [:div.date (translate @language :date)]
       [:div {:id "countdown"}
        (countdown/countdown-component @language)]

       (add-to-calendar @language)])))

(defn story
  []
  (let [language (subscribe [:current-language])
        expanded-story (subscribe [:expanded-story])]
    (fn []
      (let [text (get STORY-TEXT @language "Not found")
            trimmed-text (if @expanded-story
                              text
                              (str (subs text 0 max-story-length) "..."))]
        [:div.story.section
         [:blockquote trimmed-text]
         (if @expanded-story
           [:button.btn {:id "collapse-story"
                         :on-click #(dispatch [:set-expanded-story false])}

            (translate @language :read-less)]

           [:button.btn {:id "expand-story"
                         :on-click #(dispatch [:set-expanded-story true])}
            (translate @language :read-more)])]))))

(defn find-us
  []
  (let [language (subscribe [:current-language])]
    [:div.find-us.section
     [:p {:id "find-us-text"}
      (translate @language :find-us-text)]
     [:div {:id "map"}]]))

(defn gifts
  []
  [:div.section {:id "gifts"}
   [:div {:id "amazon-wish-list"}
    [:a {:href settings/AMAZON-WISH-LIST} "Amazon wish list"]]])

(defn contacts
  []
  (let [language (subscribe [:current-language])]
    #_[:div {:class "email"} "Email Address"]))

(def SECTIONS
  {:countdown countdown
   :story story
   :find-us find-us
   ;; :gifts gifts
   ;;:accomodation accommodation
   ;; :contacts contacts
   })

;; this is quite bootstrap specific in a way
;; would be good to extract even further
(defn navbar
  []
  (let [language (subscribe [:current-language])
        tr (fn [s] (translate @language s))]

    (fn []
      [:nav.navbar.navbar-dark.bg-primary
       [:div.container-fluid
        [:div.navbar-header
         [:a.navbar-brand {:href "#"} "Home" ]]

        (into
         [:ul.nav.navbar-nav]
         (for [sec (keys SECTIONS)]
           [:li {:key (name sec)}
            [:a {:href (str "#" (name sec))} (tr sec)]]))
        
        (into
         [:ul.nav.navbar-right {:key "language"}]
         (lang-selection @language))]])))

(defn main-panel
  []
  ;;TODO: there could be a better way to do this
  (into [:g]
        (concat
         [[navbar]]
         ;; too much vector stuff here
         (vector
          (into [:div.container]
                (map vector (vals SECTIONS)))))))


(main-panel)

(ns just-married.home.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [cemerick.url :refer [url]]
   [just-married.home.language :refer [translate]]
   [just-married.home.countdown :as countdown]
   [just-married.home.settings :as settings]
   [just-married.home.story :refer [STORY-TEXT]]))

(def ^:private max-story-length 300)

(defn- get-language
  []
  (-> js/window
      .-location
      .-href
      url
      :query
      (get "language" "en")
      keyword))

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

(defn countdown
  []
  (let [language (get-language)]
    (fn []
      [:div.countdown.section
       [:div.names "Andrea Crotti & Enrica Verrucci"]
       [:div.find-us
        [:a {:href "#find-us"} (-> settings/PLACES :wedding :name)]]

       [:div.date (translate language :date)]
       [:div {:id "countdown"}
        (countdown/countdown-component language)]

       (add-to-calendar language)])))

(defn story
  []
  (let [language (get-language)
        expanded-story (subscribe [:expanded-story])]

    (let [text (get STORY-TEXT language "Not found")
          trimmed-text (if @expanded-story
                         text
                         (str (subs text 0 max-story-length) "..."))]
      [:div.story.section
       [:blockquote trimmed-text]
       (if @expanded-story
         [:button.btn {:id "collapse-story"
                       :on-click #(dispatch [:set-expanded-story false])}

          (translate language :read-less)]

         [:button.btn {:id "expand-story"
                       :on-click #(dispatch [:set-expanded-story true])}
          (translate language :read-more)])])))

(defn find-us
  []
  [:div.find-us.section
   [:p {:id "find-us-text"}
    (translate (get-language) :find-us-text)]
   [:div {:id "map"}]])

(defn gifts
  []
  [:div.section {:id "gifts"}
   [:div {:id "amazon-wish-list"}
    [:a {:href settings/AMAZON-WISH-LIST} "Amazon wish list"]]])

(defn contacts
  []
  (let [language (get-language)]
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
  (let [language (get-language)
        tr (fn [s] (translate language s))]

    [:nav.navbar.navbar-inverse.navbar-static-top
     [:div.container-fluid
      [:div.navbar-header
       [:a.navbar-brand {:href "#"} "Home" ]]

      (into
       [:ul.nav.navbar-nav]
       (for [sec (keys SECTIONS)]
         [:li {:key (name sec)}
          [:a {:href (str "#" (name sec))} (tr sec)]]))]]))

(defn main-panel
  []
  ;;TODO: there could be a better way to do this
  (into [:g]
        (cons
         [navbar]
         (vector (into [:div.container]
                       (map vector (vals SECTIONS)))))))

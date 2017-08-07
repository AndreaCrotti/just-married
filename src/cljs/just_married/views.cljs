(ns just-married.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [just-married.language :refer [make-lang AVAILABLE-LANGUAGES]]
   [just-married.payment-views :as payment-views]
   [just-married.settings :as settings]))

(def ADDRESS "Parco Dei Principi, San Silvestro (Pescara)")

(defn header
  []
  (let [current-language (subscribe [:current-language])]
    ;; do we need the language available all over the place??
    (fn []
      ;; the actual spacing and similar stuff can be done
      ;; with CSS directly
      [:div {:id "header" :class ["col-xs" "section"]}
       [:a {:href "#story"} "Our Story"]
       [:a {:href "#find-us"} "Find us"]
       [:a {:href "#gift"} "Donate"]
       [:a {:href "#rvsp"} "RVSP"]
       [:a {:href "#share"} "Share Your Memories"]
       [:a {:href "#get-there"} "Directions"]
       [:a {:href "#accommodation"} "Accomodation"]
       [:a {:href "#contacts"}]
       ])))

(defn share
  []
  [:div {:id "share" :class "section"}
   [:div ]])

(defn story
  []
  [:div {:id "story" :class "section"}
   [:div "Andrea Crotti & Enrica Verrucci"]
   [:div [:a {:href "#find-us"} ADDRESS]]
   [:div "27th May, 2018"]])

(defn find-us
  []
  [:div {:id "find-us" :class "section"}
   [:div [:a {:href "#find-us"} ADDRESS]]
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
            :on-change #(dispatch [:name (-> % .-target .-value)])}]

   [:input {:type "email"
            :placeholder "Your email address"
            :on-change #(dispatch [:email (-> % .-target .-value)])}]

   [:button {:id "confirm-coming"
             :on-click #(dispatch [:coming])}
    "Pleasure to join you!"]

   [:button {:id "confirm-not-coming"
             :on-click #(dispatch [:not-coming])}
    "Sadly can't join you!"]
   ])

(defn lang-selection
  "Define all the possible languages as sequences of clickable images"
  []
  (let [current-language (subscribe [:current-language])]
    (fn []
      (into
       [:div {:class ["language-group"]}]
       (for [lang AVAILABLE-LANGUAGES]
         (make-lang lang @current-language))))))

;; devtools does not seem to be set up correctly
;; since it doesn't find hints.js for example
;; (log "hello")

(defn main-panel
  []
  (fn []
    [:g
     [header]
     ;; lang selection could be moved into the header potentially?
     [lang-selection]
     [story]
     [find-us]
     [gifts]
     [rvsp]]))

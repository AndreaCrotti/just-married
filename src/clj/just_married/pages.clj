(ns just-married.pages
  (:require [just-married.settings :as settings]
            [clojure.data.json :as json]))

(def ga-js (format "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                         m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', '%s', 'auto');
ga('send', 'pageview');"
                   settings/google-analytics-id))

(def google-fonts-used
  ["Open+Sans"
   "Rancho"
   "Alex+Brush"])

(defn- google-font
  [font-name]
  [:link {:rel "stylesheet"
          :href (format "//fonts.googleapis.com/css?family=%s" font-name)}])

(def text
  {:en
   {:title "Andrea & Enrica"
    :description "Wedding Andrea Crotti and Enrica Verrucci"}

   :it
   {:title "Andrea e Enrica"
    :description "Matrimonio di Andrea Crotti e Enrica Verrucci"}})

(defn header
  [env]
  [:head
   [:meta {:charset "utf-8"
           :description (:description env)}]

   [:title (:title env)]

   (google-font "Open+Sans")
   (google-font "Rancho")
   (google-font "Alex+Brush")

   [:link {:rel "stylesheet"
           :href "//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
           :integrity "sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
           :crossorigin "anonymous"}]

   ;; optional bootstrap theme
   [:link {:rel "stylesheet"
           :href "//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
           :integrity "sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
           :crossorigin "anonymous"}]

   [:link {:href "css/screen.css"
           :rel "stylesheet"
           :type "text/css"}]

   [:script {:src "//www.google.com/recaptcha/api.js"}]
   [:script {:href "//cdn.ravenjs.com/3.17.0/raven.min.js"
             :crossorigin "anonymous"}]

   [:script {:src "gmaps.js"}]
   [:script {:src "//maps.googleapis.com/maps/api/js?key=AIzaSyBmKQyNoVO3nj08cxIJMRREPDWpJxWOpgM"}]])

(def ^:private app-js [:script {:src "js/compiled/app.js"}])

(defn home-page
  [language]
  (let [env (language text)]
    [:html {:lang (name language)}

     (header env)
     [:script ga-js]
     [:body
      [:div {:id "app"}]
      ;; now we can easily generate some JS that can be then loaded by
      ;; the frontend to decide which page to display for example
      app-js
      [:script "just_married.core.init();"]
      [:script {:src "markers.js"}]]]))

(defn guest-list
  [_]
  [:html {:lang "en"}
   (header (:en text))
   [:body
    [:div {:id "app"}]
    ;; find how to render a different page with javascript
    app-js
    [:script "just_married.core.init_guests();"]]])

(defn initial-page
  [_]
  [:html {:lang "en"}
   (header (:en text))
   [:body
    [:div.initial__root
     [:div.monogram__container
      [:img {:src "images/cats_heart.jpg"
             :alt "A & E"}]]

     [:div.date__container
      "23 / 05 / 2018"]

     [:div.language__detector__english
      [:a {:href "main?language=en"}
       [:img {:src "images/gb_large.png"
              :alt "English"
              :width "100px"}]]]

     [:div.language__detector__italian
      [:a {:href "main?language=it"}
       [:img {:src "images/it_large.png"
              :width "100px"
              :alt "Italiano"}]]]]]])

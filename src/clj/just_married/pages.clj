(ns just-married.pages
  (:require [just-married.settings :as settings]))

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

   [:script {:href "//cdn.ravenjs.com/3.17.0/raven.min.js"
             :crossorigin "anonymous"}]

   [:script {:href "css/screen.css"
             :rel "stylesheet"
             :type "text/css"}]

   [:script {:src "gmaps.js"}]
   [:script {:src "//maps.googleapis.com/maps/api/js?key=AIzaSyBmKQyNoVO3nj08cxIJMRREPDWpJxWOpgM"}]

   [:script ga-js]])

(defn home-page
  [language]
  (let [env (language text)]
    [:html {:lang (name language)}

     (header env)
     [:body [:div {:id "app"}]
      ;; now we can easily generate some JS that can be then loaded by
      ;; the frontend to decide which page to display for example
      [:script {:src "js/compiled/app.js"}]
      [:script {:src "markers.js"}]]]))

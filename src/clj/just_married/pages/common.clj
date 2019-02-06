(ns just-married.pages.common
  (:require [environ.core :refer [env]]
            [just-married.settings :as settings])
  (:import (java.util UUID)))

(defn- google-font
  [font-name]
  [:link {:rel "stylesheet"
          :href (format "//fonts.googleapis.com/css?family=%s" font-name)}])

(def ga-js (format "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                         m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', '%s', 'auto');
ga('send', 'pageview');"
                   settings/google-analytics-key))

(defn- cache-buster
  [path]
  ;; fallback to a random git sha when nothing is found
  (format "%s?git_sha=%s"
          path
          (:heroku-slug-commit env (str (UUID/randomUUID)))))

(defn header
  [env]
  [:head
   [:meta {:charset "utf-8"
           :description (:description env)}]

   [:title (:title env)]

   (google-font "Open+Sans")
   (google-font "Rancho")
   (google-font "Alex+Brush")
   (google-font "Kaushan+Script")
   (google-font "Satisfy")
   (google-font "Great+Vibes")
   (google-font "Tangerine")

   [:link {:href (cache-buster "css/screen.css")
           :rel "stylesheet"
           :type "text/css"}]

   [:script {:src "//www.google.com/recaptcha/api.js"}]
   [:script {:href "//cdn.ravenjs.com/3.17.0/raven.min.js"
             :crossorigin "anonymous"}]

   ;; should be also async defer but the silly hiccup doesn't seem to
   ;; render that
   [:script {:src "css-polyfills.min.js"}]
   [:script {:src "//maps.googleapis.com/maps/api/js?key=AIzaSyBmKQyNoVO3nj08cxIJMRREPDWpJxWOpgM"}]])

(def app-js
  [:script {:src (cache-buster "js/compiled/app.js")}])

(def text
  {:en
   {:title "Marco & Elisa"
    :description "Wedding Andrea Crotti and Enrica Verrucci"}

   :it
   {:title "Marco & Elisa"
    :description "Matrimonio Marco e Elisa"}})

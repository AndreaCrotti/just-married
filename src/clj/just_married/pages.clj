(ns just-married.pages
  (:require [just-married.settings :as settings]
            [just-married.shared :refer [config]]
            [clojure.data.json :as json]
            [environ.core :refer [env]])
  (:import (java.util UUID)))

(def ga-js (format "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                         m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', '%s', 'auto');
ga('send', 'pageview');"
                   settings/google-analytics-key))

(def google-fonts-used
  ["Open+Sans"
   "Rancho"
   "Alex+Brush"])

(defn- cache-buster
  [path]
  ;; fallback to a random git sha when nothing is found
  (format "%s?git_sha=%s"
          path
          (:heroku-slug-commit env (str (UUID/randomUUID)))))

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

(def ^:private app-js
  [:script {:src (cache-buster "js/compiled/app.js")}])

(defn home-page
  [language]
  (let [env (language text)
        client-side-config (json/write-str (assoc config
                                                  :language language))]
    [:html {:lang (name language)}
     (header env)
     (when settings/google-analytics-key
       [:script ga-js])

     [:body
      [:script (format "window['config']=%s" client-side-config)]
      [:div {:id "app"}]
      ;; now we can easily generate some JS that can be then loaded by
      ;; the frontend to decide which page to display for example
      app-js
      [:script "just_married.core.init();"]
      [:script {:src "map.js"}]]]))

(defn guest-list
  [_]
  [:html {:lang "en"}
   (header (:en text))
   [:body
    [:div {:id "app"}]
    ;; find how to render a different page with javascript
    app-js
    [:script "just_married.core.init_guests();"]]])

(def ^:private date
  "27th May 2018")

(defn initial-page
  [_]
  [:html {:lang "en"}
   (header (:en text))
   (when settings/google-analytics-key
     [:script ga-js])

   [:body
    [:div.initial__root
     [:div.monogram__container
      [:img.monogram {:src "images/monogram_navy.png"
                      :alt "Andrea & Enrica"}]]

     [:div.date__container date]
     [:div.language__detector__english
      [:a {:href "main?language=en"}
       [:img.flag {:src "images/gb_large.png"
                   :alt "English"}]]]

     [:div.language__detector__italian
      [:a {:href "main?language=it"}
       [:img.flag {:src "images/it_large.png"
                   :alt "Italiano"}]]]]]])

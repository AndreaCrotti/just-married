(ns just-married.pages.enter
  (:require [just-married.settings :as settings]
            [just-married.language :refer [get-language]]
            [just-married.pages.common :as common]))

(def ^:private date
  {:en "22nd December 2018"
   :it "22 Dicembre 2018"})

(defn- make-url
  [language redirect-to]
  (let [url (format "main?language=%s" language)
        full-url (if redirect-to
                   (clojure.string/join "#" [url redirect-to])
                   url)]

    full-url))

(defn initial-page
  [{:keys [language redirect-to]}]
  (let [env (language common/text)]
    [:html {:lang (name language)}
     (common/header env)
     (when settings/google-analytics-key
       [:script common/ga-js])

     [:body
      [:div.initial__root
       [:div.monogram__container
        [:img.monogram {:src "images/marco_elisa.jpeg"
                        :alt "Marco & Elisa"}]]

       [:div.date__container (language date)]
       #_[:div.language__detector__english
          [:a {:href (make-url "en" redirect-to)}
           [:img.flag {:src "images/gb_large.png"
                       :alt "English"}]]]

       [:div.language__detector__italian
        [:a.enter__link {:href (make-url "it" redirect-to)}
         "ENTRA"]]]]]))

(ns just-married.pages.home
  (:require [just-married.settings :as settings]
            [just-married.pages.common :as common]
            [just-married.shared :refer [config]]
            [clojure.data.json :as json]))

(defn home-page
  [{:keys [language]}]
  (let [env (language common/text)
        client-side-config (json/write-str (assoc config
                                                  :language language))]
    [:html {:lang (name language)}
     (common/header env)
     (when settings/google-analytics-key
       [:script common/ga-js])

     [:body
      [:script (format "window['config']=%s" client-side-config)]
      [:div {:id "app"}]
      ;; now we can easily generate some JS that can be then loaded by
      ;; the frontend to decide which page to display for example
      common/app-js
      [:script "just_married.core.init();"]
      [:script {:src "map.js"}]]]))

(ns just-married.pages.guests
  (:require [just-married.pages.common :as common]))

(defn guest-list
  [_]
  [:html {:lang "en"}
   (common/header (:en common/text))
   [:body
    [:div {:id "app"}]
    ;; find how to render a different page with javascript
    common/app-js
    [:script "just_married.core.init_guests();"]]])

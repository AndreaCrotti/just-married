(ns just-married.home.subs
  (:require [re-frame.core :as re-frame :refer [reg-sub]]))

;; get the current language
(reg-sub
 :current-language
 ;; is it the same as simply saying ":language"
 :language)

(reg-sub
 :expanded-story
 :expanded-story)

(ns just-married.subs
  (:require [re-frame.core :as re-frame :refer [reg-sub]]))

;; get the current language
(reg-sub
 :current-language
 :language)

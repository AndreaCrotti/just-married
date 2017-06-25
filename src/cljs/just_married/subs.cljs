(ns just-married.subs
  (:require [re-frame.core :as re-frame :refer [reg-sub]]))

(reg-sub
 :current-language
 :language)

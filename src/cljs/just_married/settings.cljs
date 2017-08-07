(ns just-married.settings
  (:require [cljs-time.core :as time]))

(def GOOGLE-MAPS-KEY "AIzaSyBmKQyNoVO3nj08cxIJMRREPDWpJxWOpgM")

(def AMAZON-WISH-LIST "http://amzn.eu/hzWt6gk")

(def PLACES
  {:parco {:name "Parco Dei Principi, San Silvestro (Pescara)"
           :latitude "42.423608"
           :longitude "14.231041"}}
  ;; add the list of suggested places to sleep here
  )

(def FATIDIC-TIME
  (time/date-time 2018 05 27 13))


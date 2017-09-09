(ns just-married.settings
  (:require [cljs-time.core :as time]))

(def GOOGLE-MAPS-KEY "AIzaSyBmKQyNoVO3nj08cxIJMRREPDWpJxWOpgM")

(def AMAZON-WISH-LIST "http://amzn.eu/hzWt6gk")

(def PLACES
  {:parco {:name "Parco Dei Principi, San Silvestro (Pescara)"
           :latitude "42.423608"
           :longitude "14.231041"
           :url "http://www.masgrandieventi.it/matrimoni-banchetti/portfolio_page/parco-dei-principi/"}}
  ;; add the list of suggested places to sleep here
  )

(def FATIDIC-TIME
  (time/date-time 2018 05 27 13))

(def RECAPTCHA-KEY "6LdKcy0UAAAAAP6M0eWoFblIxnyJVTVxIQIfdilx")

(def WEDDING-DAY
  {:en "https://calendar.google.com/calendar/event?action=TEMPLATE&tmeid=N2NtOHFmYm1tYnIzamY2Zzc4aDFnMWl2NXMgdWQ2bmRiMWhnNWlyMzI5bWZsZzc5cWwxbDRAZw&tmsrc=ud6ndb1hg5ir329mflg79ql1l4%40group.calendar.google.com"
   :it "https://calendar.google.com/calendar/event?action=TEMPLATE&tmeid=M2doaHExNTQwbWM3ZzIyaGt0YnRraXFlc2kgdWQ2bmRiMWhnNWlyMzI5bWZsZzc5cWwxbDRAZw&tmsrc=ud6ndb1hg5ir329mflg79ql1l4%40group.calendar.google.com"})

(def GOOGLE-CALENDAR-IMG "https://www.google.com/calendar/images/ext/gc_button1_it.gif")

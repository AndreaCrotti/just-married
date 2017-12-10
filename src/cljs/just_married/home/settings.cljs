(ns just-married.home.settings
  (:require [cljs-time.core :as time]))

(def GOOGLE-MAPS-KEY "AIzaSyBmKQyNoVO3nj08cxIJMRREPDWpJxWOpgM")

(def AMAZON-WISH-LIST "http://amzn.eu/hzWt6gk")

(def PLACES
  {:wedding {:name "Parco Dei Principi, San Silvestro (Pescara)"
             :latitude "42.423608"
             :longitude "14.231041"
             :url "http://www.masgrandieventi.it/matrimoni-banchetti/portfolio_page/parco-dei-principi/"}
   ;; add the list of suggested places to sleep here
   :guests
   {:villa-maria {:name "Villa Maria Hotel Spa"
                  :latitude "42.438338"
                  :longitude "14.249940"
                  :url "http://www.hvillamaria.it/en/"}

    :villa-aurora {:name "Villa l'Aurora"
                   :url "http://www.villaurorabruzzo.it/villa_aurora.php"
                   ;; via della riviera 99
                   ;; 42.477345, 14.205415
                   :latitude "42.477345"
                   :longitude "14.205415"}

    :maison-fleurie {:name "Maison Fleurie"
                     :url "https://www.booking.com/hotel/it/maison-fleurie.en-gb.html"
                     :latitude "42.472571"
                     :longitude "14.207303"}}})

(def FATIDIC-TIME
  (time/date-time 2018 05 27 13))

(def RECAPTCHA-KEY "6LdKcy0UAAAAAP6M0eWoFblIxnyJVTVxIQIfdilx")

;; we could construct the url from the inner components in theory?
(def WEDDING-DAY
  {:en "https://calendar.google.com/calendar/event?action=TEMPLATE&tmeid=N2NtOHFmYm1tYnIzamY2Zzc4aDFnMWl2NXMgdWQ2bmRiMWhnNWlyMzI5bWZsZzc5cWwxbDRAZw&tmsrc=ud6ndb1hg5ir329mflg79ql1l4%40group.calendar.google.com"
   :it "https://calendar.google.com/calendar/event?action=TEMPLATE&tmeid=M2doaHExNTQwbWM3ZzIyaGt0YnRraXFlc2kgdWQ2bmRiMWhnNWlyMzI5bWZsZzc5cWwxbDRAZw&tmsrc=ud6ndb1hg5ir329mflg79ql1l4%40group.calendar.google.com"})

(def GOOGLE-CALENDAR-IMG "https://www.google.com/calendar/images/ext/gc_button1_it.gif")

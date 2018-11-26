(ns just-married.shared
  (:require [just-married.geo-info :as geo]))

(def available-languages
  "All the available languages"
  #{:en :it})

(def config
  {:places geo/places
   :zoom geo/zoom
   :map-type-id geo/map-type-id
   :center geo/map-center
   :maps geo/map-configs})

(def sections
  [:countdown
   :find-us
   :timeline
   ;; :accommodation
   :gift
   ;;:rvsp
   :social])

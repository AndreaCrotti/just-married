(ns just-married.home.maps
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame :refer [dispatch subscribe]]))

(def places
  [{:lat 42.346799
    :lng 14.164534
    :title "Palazzo Lepri"
    :icon "images/rings_small.png"
    :info "Palazzo Lepri"}])

(def map-options
  {:zoom 2
   :mapTypeId "roadmap"})

(defn gmap-component []
  (let [gmap    (atom nil)
        options (clj->js map-options)
        update  (fn [comp]
                  (let [{:keys [latitude longitude]} (reagent/props comp)
                        latlng (js/google.maps.LatLng. latitude longitude)]
                    (.setPosition (:marker @gmap) latlng)
                    (.panTo (:map @gmap) latlng)))]

    (reagent/create-class
     {:reagent-render (fn []
                        [:div
                         ;; [:h4 "Map"]
                         ;; move this to CSS instead
                         [:div#map-canvas {:style {:height "400px"}}]])

      :component-did-mount (fn [comp]
                             (let [canvas  (.getElementById js/document "map-canvas")
                                   gm      (js/google.maps.Map. canvas options)
                                   marker  (js/google.maps.Marker. (clj->js {:map gm :title "Drone"}))]
                               (reset! gmap {:map gm :marker marker}))
                             (update comp))

      :component-did-update update
      :display-name "gmap-component"})))

(defn gmap-wrapper []
  (let [pos (subscribe [:current-position])]
    (fn []
      [gmap-component @pos])))

;; (def places
;;   [{:lat 42.346799
;;     :lng 14.164534
;;     :title "Palazzo Lepri"
;;     :icon "images/rings_small.png"
;;     :info "Palazzo Lepri"}])

;; (def map-options
;;   {"zoom" 12
;;    "mapTypeId" "roadmap"})

;; (def gmap (google.maps.Map. (.getElementById js/document "map") map-options))
;; (def mm (google.maps.Map. (.getElementById js/document "map")))

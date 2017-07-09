(ns just-married.maps
  (:require [reagent]))

(def GOOGLE-MAPS-KEY
  "AIzaSyCuKlf0JMp94GtD3MFknJIJqYhAmvLzcgY")

;; look at the example from:
;; https://gist.github.com/jhchabran/e09883c3bc1b703a224d#file-2_google_map-cljs
(defn gmap-component []
  (let [gmap    (atom nil)
        options (clj->js {"zoom" 9})
        update  (fn [comp]
                  (let [{:keys [latitude longitude]} (reagent/props comp)
                        latlng (js/google.maps.LatLng. latitude longitude)]
                    (.setPosition (:marker @gmap) latlng)
                    (.panTo (:map @gmap) latlng)))]

    (reagent/create-class
     {:reagent-render (fn []
                        [:div
                         [:h4 "Map"]
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

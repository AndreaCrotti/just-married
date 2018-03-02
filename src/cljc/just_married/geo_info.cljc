(ns just-married.geo-info)

(def map-center {:lat 42.4
                 :lng 14.2})

(def zoom 12)
(def map-type-id "roadmap")

(def places
  {:lepri {:lat 42.346799
           :lng 14.164534
           :title "Palazzo Lepri"
           :icon "images/rings_small.png"
           :info "Palazzo Lepri"
           :address "Via la Valletta, 6, 66100 Chieti CH, Italy"
           :website "http://www.palazzolepri.it/"
           :description {:it "Palazzo Lepri"
                         :en "Lepri Palace"}}

   :princi {:lat 42.423608
            :lng 14.231041
            :address "Strada Provinciale per Pescara - S. Silvestro, 320, 65100 Pescara PE, Italy"
            :website "http://www.masgrandieventi.it/"
            :title "Parco dei Principi"
            :icon "images/party_small.png"
            :info "Parco dei Principi"}

   :villa {:lat 42.421552
           :lng 14.230742
           :address "Strada Comunale San Silvestro, 340, 65129 Pescara PE, Italy"
           :website "http://www.hotelvillaimmacolata.it/"
           :icon "images/hotel_small.png"
           :title "Parc Hotel Villa Immacolata"
           :info "Hotel Villa Immacolata"}

   ;;TODO: get the right coordinates
   :vignaiole {:lat 42.421552
               :lng 14.230742
               :icon "images/vignaiole.png"
               :info "B & B"}

   :pescara-airport {:lat 42.421552
                     :lng 14.230742
                     :icon "images/airport.png"
                     :info "Aeroporto di Pescara"}

   :pescara-station {:lat 42.421552
                     :lng 14.230742
                     :icon "images/stazione.png"
                     :info "Stazione di Pescara"}})

(def map-configs
  {:wedding {:element-id "map"
             :places [:lepri :princi]
             :center {:lat 42.4
                      :lng 14.2}
             :zoom 12
             :map-type-id "roadmap"}

   :accommodation {:element-id "accommodation-map"
                   :places [:princi :villa]
                   :center {:lat (-> places :villa :lat)
                            :lng (-> places :villa :lng)}
                   :zoom 15
                   :map-type-id "roadmap"}

   ;; use this merged map somewhere
   :merged-map {:element-id "full-map"
                :places (keys places)
                :center {:lat 42.42
                         :lng 14.23}
                :zoom 11
                :map-type-id "roadmap"}})

(defn place-detail
  [name]
  (let [place-info (name places)]
    [:div.place-detail
     [:img.timeline__icon {:src (:icon place-info)}]
     [:span.place__name (str (:title place-info) ": ")]
     [:div.place__address (:address place-info)]
     [:span.place__website
      [:a {:href (:website place-info)} (:website place-info)]]]))

(defn place-detail-list
  [places]
  (into [:ul]
        (for [k places]
          [:li (place-detail k)])))

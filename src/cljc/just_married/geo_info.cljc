(ns just-married.geo-info)

(def map-center {:lat 42.4
                 :lng 14.2})

(def zoom 12)
(def map-type-id "roadmap")

(def places
  {:palazzo {:lat 45.321197
             :lng 10.658947
             :title "Palazzo Gonzaga Guerrieri"
             :icon "images/rings_small.png"
             :info "Palazzo Gonzaga Guerrieri"
             :address "Via Beata Paola Montaldi, 15, 46049 Volta Mantovana MN"
             :website "http://www.enotecagonzaga.com/"}

   :satakunta {:lat 45.370703
               :lng 10.580631
               :icon "images/party_small.png"
               :title "Ristorante Satakunta"
               :info "Ristorante Satakunta"
               :address "Via Parolara, 2, 46040 Cavriana MN"
               :website "http://satakunta.it/"}})

(def map-configs
  {:wedding {:element-id "map"
             :places [:satakunta :palazzo]
             :center {:lat 45.34
                      :lng 10.6}
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

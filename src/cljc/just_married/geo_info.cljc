(ns just-married.geo-info)

(def map-center {:lat 42.4
                 :lng 14.2})

(def zoom 12)
(def map-type-id "roadmap")

(def places
  {:lepri {:lat 42.346799,
           :lng 14.164534,
           :title "Palazzo Lepri",
           :icon "images/rings_small.png",
           :info "Palazzo Lepri"}

   :princi {:lat 42.423608
            :lng 14.231041
            :title "Parco dei Principi"
            :icon "images/party_small.png"
            :info "Parco dei Principi"}

   :villa {:lat 42.421552
           :lng 14.230742
           :icon "images/hotel_small.png"
           :info "Hotel Villa Immacolata"}})

(def map-configs
  {:wedding {:element-id "map"
             :places [:lepri :princi]
             :center {:lat 42.4
                      :lng 14.2}
             :zoom 12
             :map-type-id "roadmap"}

   :accommodation {:element-id "accommodation-map"
                   :places [:princi :villa]
                   :center {:lat 42.4
                            :lng 14.2}
                   :zoom 13
                   :map-type-id "roadmap"}})

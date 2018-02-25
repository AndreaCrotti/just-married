(ns just-married.home.accommodation
  (:require [just-married.home.language :refer [translate]]
            [goog.string]
            [re-frame.core :refer [dispatch subscribe]]))

;; keeping this here in case we want to show all the information available?
(def prices
  {:single    58.5
   :double    71
   :triple    89
   :quadruple 107})

(def email-hotel "info@villaimmacolata.it")

(def ^:private accommodation-dict
  {:en {:title    "Accommodation"
        :suggest  "We suggest booking at the Hotel Villa Immacolata."
        :code     "The code to get a 10% discount is: \"matrimonio Verrucci\" and prices range from 58 € (per night) for the single to the 89 € for the triple room, breakfast included."
        :distance "As you can see from the map from Villa Immacolata you can even walk to the party venue, and we will organise a Bus from there to the place of the ceremony."
        :contact  "Contact the hotel at %s for booking and more information."}

   :it {:title   "Dove Dormire"
        :suggest "Suggeriamo di pernottare all'Hotel Villa Immacolata."
        ;;TODO: verify if the code can be used directly on booking.com or how else
        :code    "Il codice per ottenere uno sconto del 10% è: \"matrimonio Verrucci\", e i prezzi variano dai 58 € (a notte) per la singola ai 89 € per la tripla, colazione inclusa."

        :distance "Come si puó vedere dalla mappa Villa Immacolata é a soli 5 minuti a piedi dal Parco dei Principi (dove si svolge la festa), e ci sarà un Pullman organizzato che porta dall'Hotel al luogo della cerimonia."
        :contact  "Potete contattare l'hotel alla mail %s per prenotare e per ulteriori informazioni"}})

(def ^:private tr (partial translate accommodation-dict))

(defn accommodation
  []
  [:div.accommodation {:id "accommodation"}
   [:h3 (tr :title)]
   [:div.accommodation__villa-immacolata
    [:div (tr :suggest)]
    [:div (tr :code)]
    [:div (tr :distance)]
    [:a {:href (goog.string/format "mailto:%s" email-hotel)}
     (goog.string/format (tr :contact) email-hotel)]]
   
   [:div.google-map {:id "accommodation-map"}]])

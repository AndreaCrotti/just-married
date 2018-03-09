(ns just-married.api
  (:gen-class)
  (:require [clojure.walk :refer [keywordize-keys]]
            [clojure.string :as string]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [compojure.core :refer [defroutes GET POST]]
            [environ.core :refer [env]]
            [hiccup.core :as html]
            [ring.middleware.defaults :as r-def]
            [ring.middleware.resource :as resources]
            [ring.middleware.json :refer [wrap-json-params wrap-json-response]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty]
            [just-married.auth :refer [with-basic-auth basic-auth-backend]]
            [just-married.language :refer [detect-language]]
            [just-married.shared :refer [available-languages]]
            [just-married.pages.home :as home-page]
            [just-married.pages.guests :as guest-page]
            [just-married.pages.enter :as enter]
            [just-married.api.labels :refer [labels-api]]
            [just-married
             [settings :as settings]
             [db :as db]
             [mail :refer [send-email]]]))

(defn get-language
  [request]
  (detect-language
   (get-in request [:headers "accept-language"])
   available-languages))

(def pages
  {:guests  guest-page/guest-list
   :home    home-page/home-page
   :initial enter/initial-page})

(defn- render-page
  [page kwargs]
  (-> (resp/response (html/html ((page pages) kwargs)))
      (resp/content-type "text/html")))

(def ^:private secure? (= (env :secure) "true"))
(def ^:private default-port 3000)

(defn- get-port
  []
  (Integer. (or (env :port) default-port)))

(defn enter-page
  [request & {:keys [redirect-to]}]
  (let [language (get-language request)]
    (render-page :initial {:language language
                           :redirect-to redirect-to})))

(defn main-page
  [request]
  (let [preferred-language (get-language request)
        current-language (-> request :params :language keyword)]

    (if (nil? current-language)
      (resp/redirect (format "/main?language=%s" (name preferred-language)))
      (render-page :home {:language preferred-language}))))

(defn notify
  [request]
  (let [params
        (-> request
            :params
            ;; this keywordize should not be
            ;; actually needed in theory
            keywordize-keys)]
    (log/info "passing to send-emails parameters"
              params)

    {:status 201
     :body   (-> (send-email params)
                 :message)}))

(defn confirm
  [request]
  (let [params (-> request
                   :params
                   keywordize-keys)]

    {:status 201
     :body   "Done"}))

(defn guest-list
  "Page showing the list of guests, needs to be authenticated"
  [request]
  (with-basic-auth request
    (render-page :guests :en)))

(defn guest-list-api
  [request]
  (let [guests (->> (db/all-guests!)
                    (map #(select-keys % [:id :first_name :last_name])))] (-> {:status 200
                                                                               :body   guests}
                                                                              (resp/content-type "application/edn"))))

(defroutes app-routes
  (GET "/" request (enter-page request))
  (GET "/rvsp" request (enter-page request :redirect-to "rvsp"))
  (POST "/notify" request (notify request))
  (POST "/confirm" request (confirm request))
  ;; do a redirect adding the extra information
  (GET "/main" request (main-page request))
  (GET "/guests" request (guest-list request))
  (GET "/api/labels" request (labels-api request))
  (GET "/api/guests" request (guest-list-api request)))

(def app
  (-> app-routes
      (resources/wrap-resource "public")
      (r-def/wrap-defaults (if secure?
                             r-def/secure-site-defaults
                             (assoc-in r-def/site-defaults [:security :anti-forgery] false)))

      (wrap-authorization basic-auth-backend)
      (wrap-authentication basic-auth-backend)
      wrap-keyword-params
      wrap-json-params))

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))

(ns just-married.test-service
  (:require [just-married.service :as service]
            [clojure.test :as t]
            [io.pedestal.http :as http]
            [io.pedestal.test :as pt]))


(def test-service
  (::http/service-fn (http/create-servlet service/service)))

(t/deftest home-page-test
  (t/is
   (= (:status (pt/response-for test-service :get "/")) 200)
   (= (:status (pt/response-for test-service :post "/")) 405)))
;; now check the headers

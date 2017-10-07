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

(t/deftest confirmation-test
  (let [confirm-positive (pt/response-for
                          test-service
                          :post "/confirm"
                          :body {"coming" true
                                 "email" "friend@mail.com"
                                 "name" "friend"})]
    ;; this should actually fail!
    (t/is
     (= (:status confirm-positive 201)))))

(ns just-married.api-test
  (:require [clojure.test :as t]
            [clojure.string :refer [includes?]]
            [ring.mock.request :as mock]
            [just-married.api :refer [app]]
            [just-married.utils :refer [db-reachable?]]
            [clojure.java.io :as jio]))

(t/deftest homepage-test
  (let [req (mock/request :get "/")
        resp (app req)]

    (t/testing "Homepage returns 200"
      (t/is (= 200 (-> resp :status))))

    (t/testing "Homepage contains some valid content"
      (t/is (true? (includes?
                    (-> resp :body)
                    "Andrea"))))))

(t/deftest authentication-helpers-test
  (t/testing "Can happily authenticate"
    (let [req (mock/request :get "/login" {:password "wrong"})
          resp (app req)]))
  (t/testing "Not able to authenticate"
    (let [req (mock/request :get "/login" {:password "secure-password"})
          resp (app req)])))

(when db-reachable?
  (t/deftest guest-list-test
    (t/testing "Without being authenticated we get 401"
      (let [req (mock/request :get "/guests")
            resp (app req)]
        (t/is (= 401 (-> resp :status)))))

    (t/testing "With authentication we get a 200"
      (let [req (mock/request :get "/guests")
            authed-req (assoc req :identity "admin")
            resp (app authed-req)]
        (t/is (= 200 (-> resp :status)))
        (t/is (= [] (-> resp :body)))))))

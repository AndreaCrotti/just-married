(ns just-married.api-test
  (:require [clojure.test :as t]
            [clojure.string :refer [includes?]]
            [ring.mock.request :as mock]
            [just-married.api :as sut]
            [just-married.utils :refer [db-reachable?]]
            [clojure.java.io :as jio]))

(t/deftest homepage-test
  (let [req (mock/request :get "/")
        resp (sut/app req)]

    (t/testing "Homepage returns 200"
      (t/is (= 200 (-> resp :status))))

    (t/testing "Homepage contains some valid content"
      (t/is (true? (includes?
                    (-> resp :body)
                    "Andrea"))))))

(t/deftest language-detection-test
  (t/testing "Detect language and redirect to the right one"
    (let [req (-> (mock/request :get "/main")
                  (mock/header "accept-language" "it"))
          resp (sut/app req)]
      (t/is (= (-> resp :status) 302))
      (t/is (true? (clojure.string/includes?
                    (get-in resp [:headers "Location"])
                    "main?language=it")))))

  (t/testing "No redirect if language set manually"
    (let [req (mock/request :get "/main?language=it")
          resp (sut/app req)]
      (t/is (= (-> resp :status) 200)))))

(t/deftest authentication-helpers-test
  (t/testing "Can happily authenticate"
    (let [req (mock/request :get "/login" {:password "wrong"})
          resp (sut/app req)]))
  (t/testing "Not able to authenticate"
    (let [req (mock/request :get "/login" {:password "secure-password"})
          resp (sut/app req)])))

(when db-reachable?
  (t/deftest guest-list-test
    (t/testing "Without being authenticated we get 401"
      (let [req (mock/request :get "/guests")
            resp (sut/app req)]
        (t/is (= 401 (-> resp :status)))))

    (t/testing "With authentication we get a 200"
      (let [req (mock/request :get "/guests")
            authed-req (assoc req :identity "admin")
            resp (sut/app authed-req)]
        (t/is (= 200 (-> resp :status)))
        (t/is (clojure.string/includes? (-> resp :body) "init_guests()"))))))

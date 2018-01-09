(ns just-married.api-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [includes?]]
            [ring.mock.request :as mock]
            [just-married.api :as sut]
            [just-married.utils :refer [db-reachable?]]
            [clojure.java.io :as jio]))

(defn- body-includes?
  [response s]
  (includes? (-> response :body) s))

(deftest homepage-test
  (let [req (mock/request :get "/")
        resp (sut/app req)]

    (testing "Homepage returns 200"
      (is (= 200 (-> resp :status))))

    (testing "Homepage contains some valid content"
      (is (true? (body-includes? resp "Andrea")))))

  (testing "/rvsp redirects directly to the rvsp anchor"
    (let [req-rvsp (mock/request :get "/rvsp")
          resp (sut/app req-rvsp)]

      (is (true? (body-includes? resp "main?language=en#rvsp"))))))

(deftest language-detection-test
  (testing "Detect language and redirect to the right one"
    (let [req (-> (mock/request :get "/main")
                  (mock/header "accept-language" "it"))
          resp (sut/app req)]
      (is (= (-> resp :status) 302))
      (is (true? (clojure.string/includes?
                    (get-in resp [:headers "Location"])
                    "main?language=it")))))

  (testing "No redirect if language set manually"
    (let [req (mock/request :get "/main?language=it")
          resp (sut/app req)]
      (is (= (-> resp :status) 200)))))

(deftest authentication-helpers-test
  (testing "Can happily authenticate"
    (let [req (mock/request :get "/login" {:password "wrong"})
          resp (sut/app req)]))
  (testing "Not able to authenticate"
    (let [req (mock/request :get "/login" {:password "secure-password"})
          resp (sut/app req)])))

(deftest notify-test
  (testing "Send a notification"
    (let [req
          (-> (mock/request :post "/notify")
              (mock/json-body {:email "friend@mail.com"
                               :name "friend"}))
          resp (sut/app req)]

      (is (= (-> resp :status) 201)))))

(when db-reachable?
  (deftest guest-list-test
    (testing "Without being authenticated we get 401"
      (let [req (mock/request :get "/guests")
            resp (sut/app req)]
        (is (= 401 (-> resp :status)))))

    (testing "With authentication we get a 200"
      (let [req (mock/request :get "/guests")
            authed-req (assoc req :identity "admin")
            resp (sut/app authed-req)]
        (is (= 200 (-> resp :status)))
        (is (clojure.string/includes? (-> resp :body) "init_guests()"))))))

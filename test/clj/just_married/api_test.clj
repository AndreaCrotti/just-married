(ns just-married.api-test
  (:require [clojure.test :refer [deftest is testing use-fixtures]]
            [clojure.string :refer [includes?]]
            [ring.mock.request :as mock]
            [just-married.api :as sut]
            [just-married.db :as db]
            [just-married.db-setup :refer [setup]]
            [clojure.java.io :as jio])
  (:import (java.util UUID)))

(use-fixtures :each setup)

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

  #_(testing "/rvsp redirects directly to the rvsp anchor"
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

(deftest list-of-guests-test
  (testing "No guests is empty set"
    (let [req  (mock/request :get "/api/guests")
          resp (sut/app req)]

      (is (= {:status 200 :body ()} (dissoc resp :headers)))))

  (testing "Adding a guest allows to return it"
    (let [group-id (UUID/randomUUID)
          guest-id (UUID/randomUUID)]

      (db/add-guest-group! {:id         group-id
                            :group-name "sample group"
                            :invited-by "andrea"})

      (db/add-guest! {:id         guest-id
                      :first-name "Mario"
                      :last-name  "Bros"
                      :group-id   group-id})

      (let [req     (mock/request :get "/api/guests")
            resp    (sut/app req)
            desired {:status 200,
                     :body
                     [{:id         guest-id,
                       :first_name "Mario",
                       :last_name  "Bros"}]}]

        (is (= desired (dissoc resp :headers)))))))

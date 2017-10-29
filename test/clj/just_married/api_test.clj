(ns just-married.api-test
  (:require [clojure.test :as t]
            [clojure.string :refer [includes?]]
            [ring.mock.request :as mock]
            [just-married.api :refer [app]]
            [clojure.java.io :as jio]))

(t/deftest homepage-test
  (let [req (mock/request :get "/")
        resp (app req)]

    (t/testing "Homepage returns 200"
      (t/is (= 200 (-> resp :status))))

    (t/testing "Homepage contains some valid content"
      (t/is (true? (includes?
                    (slurp
                     (.toString (:body resp)))
                    "Andrea"))))))

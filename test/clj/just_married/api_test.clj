(ns just-married.api-test
  (:require [just-married.api :as api]
            [clojure.test :as t]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [just-married.settings :refer [loaded?]]))

(t/deftest test-email
  (t/testing "sending email using the API"

    (let [request (mock/request :post "/send-email"
                                {:content "hello from test"})
          response (api/app request)]

      ;; simple way to just skip the tests running on travis
      (when (loaded?)
        (t/is (= (:status response) 200))))))

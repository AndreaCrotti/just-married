(ns just-married.core-test
  (:require [just-married.core :as core]
            #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t :include-macros true])))

(t/deftest core-test
  (t/testing "math"
    (t/is (= 1 (inc 0)))))

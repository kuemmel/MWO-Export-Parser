(ns mwo-parse-export.convert-test
  (:require [mwo-parse-export.convert :as convert])
  (:use [clojure.test]))

(deftest base64-digit->base10
  (let [pair-equal (fn [[re-converted-base10 base10]] (= re-converted-base10 base10))
        get-base10-base64-pairs (map (fn [x] (vector x (char (+ x (int \0))))) (range 0 63))]
    (testing "All valid inputs"
      (is (every? pair-equal
                 (map (fn [[base10 base64]] (vector base10 (convert/base64-digit->base10 base64)))
                      get-base10-base64-pairs))))
    (testing "Invalid inputs"
      (is (thrown? IllegalArgumentException (convert/base64-digit->base10 64))))))

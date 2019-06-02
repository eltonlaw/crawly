(ns crawly.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [crawly.dev :as dev]
            [crawly.test-handlers :as test-handlers]
            [crawly.core :as core]))

(deftest set-cache-level!
  (testing "Valid level passed in"
    (is (= (core/set-cache-level! :aggressive)
           :aggressive)))
  (testing "Invalid level passed in"
    (try
      (core/set-cache-level! 5)
      (catch Exception e
        (is (= (ex-data e)
               {:causes {:invalid-level 5}}))))))

(deftest GET
  (dev/with-server [server test-handlers/basic]
    (is (= "<!DOCTYPE html>\n<html><body><div>some content</div></body></html>"
           (core/GET "http://localhost:3000")))))

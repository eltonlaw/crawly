(ns crawly.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [crawly.cache :as cache]
            [crawly.core :as core]
            [crawly.dev :as dev]
            [crawly.test-handlers :as test-handlers]))

(deftest set-cache-level!
  (testing "Valid level passed in"
    (is (= (core/set-cache-level! :aggressive)
           :aggressive))
    (core/set-cache-level! :none))
  (testing "Invalid level passed in"
    (try
      (core/set-cache-level! 5)
      (catch Exception e
        (is (= (ex-data e)
               {:causes {:level 5}}))))))

(deftest GET
  (testing "basic GET request returns body"
    (dev/with-server [server test-handlers/basic]
      (is (= "<!DOCTYPE html>\n<html><body><div>some content</div></body></html>"
             (-> (core/GET "http://localhost:3000") :body)))))
  (testing "GET is cached"
    (core/set-cache-level! :aggressive)
    (dev/with-server [server test-handlers/basic]
      (cache/delete-dir-files @cache/path)
      (let [tmpdir-filecount #(or (count (.listFiles (clojure.java.io/as-file @cache/path))) 0)]
        (is (= 0 (tmpdir-filecount)))
        (core/GET "http://localhost:3000")
        (is (= 1 (tmpdir-filecount)))))
    (core/set-cache-level! :none)))

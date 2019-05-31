(ns crawly.dev-test
  (:require [clojure.test :refer [deftest is testing]]
            [crawly.dev :as dev]
            [crawly.test-handlers :as test-handlers]))

(deftest start-stop-server
  (testing "At each step, check that state of `servers` atom is as expected"
    (testing "Start server & stop by key"
      (is (empty? @dev/servers))
      (dev/start-server! test-handlers/basic)
      (is ((complement empty?) @dev/servers))
      (dev/stop-servers! test-handlers/basic)
      (is (empty? @dev/servers)))
    (testing "Start servers & stop all by passing no argument"
      (is (empty? @dev/servers))
      (dev/start-server! test-handlers/basic)
      (dev/start-server! test-handlers/basic {:port 3001 :join? false})
      (is ((complement empty?) @dev/servers))
      (dev/stop-servers!)
      (is (empty? @dev/servers)))))

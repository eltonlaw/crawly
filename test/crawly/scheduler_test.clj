(ns crawly.scheduler-test
  (:require [clojure.test :refer [deftest is testing]]
            [crawly.core :as crawly]
            [crawly.dev :as dev]
            [crawly.scheduler :as scheduler]
            [crawly.test-handlers :as test-handlers]))

(deftest scheduler
  (dev/with-server [server test-handlers/links]
    (let [urls [{:run crawly/GET :payload "http://localhost:3000"}
                {:run crawly/GET :payload "http://localhost:3000/a"}
                {:run crawly/GET :payload "http://localhost:3000/b"}
                {:run crawly/GET :payload "http://localhost:3000/c"}]]
      (is (= 4 (count (scheduler/scheduler urls)))))))

(ns crawly.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [crawly.dev :as dev]
            [crawly.test-handlers :as test-handlers]
            [crawly.core :as core]))

(deftest GET
  (dev/with-server [server test-handlers/basic]
    (is (= "<!DOCTYPE html>\n<html><body><div>some content</div></body></html>"
           (core/GET "http://localhost:3000")))))

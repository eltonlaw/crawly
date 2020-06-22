(ns crawly.core-test
  (:require [crawly.core :as core]
            [clojure.core.async :refer [<!!]]
            [clojure.test :refer [deftest is]]))

(deftest ^:integration GET
  (let [job-id-1 (core/GET "http://localhost:9500/#/")]
    (is (= 200 (get-in (<!! core/response-ch) [:response :status])))))

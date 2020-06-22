(ns crawly.core-test
  (:require [crawly.core :as core]
            [clojure.core.async :refer [<!!]]
            [clojure.test :refer [deftest is]]))

(core/process-jobs-worker!)

(deftest ^:integration GET
  (let [job-id-1 (core/GET "http://localhost:9500/#/")]
    (Thread/sleep 1000) ;; FIXME: Dirty hack
    (is (= 200 (get-in @core/jobs [job-id-1 :response :status])))))

(ns crawly.core
  (:require [clj-http.client :as client]))

(defn GET
  [url]
  (let [response (client/get url)]
    (if (= (:status response) 200)
      (:body response))))



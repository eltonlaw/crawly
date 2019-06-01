(ns crawly.core
  (:require [clojure.spec.alpha :as s]
            [clj-http.client :as client]
            [taoensso.timbre :refer [info]]))

(s/fdef GET
  :args (s/cat ::url string?)
  :ret (s/nilable string?))

(defn GET
  [url]
  (let [response (client/get url)]
    (if (= (:status response) 200)
      (:body response)
      (info "Non 200 response: " response))))

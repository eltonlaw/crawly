(ns crawly.validate
  (:require [taoensso.timbre :refer [info]]))

(defn conform [response]
  (if (not= (:status response) 200)
    (do
      (info "Non 200 response: " response)
      :invalid-response)
    response))

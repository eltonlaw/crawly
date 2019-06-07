(ns crawly.scheduler
  (:require [clojure.spec.alpha :as s]
            [taoensso.timbre :refer [info]]))

(s/fdef scheduler
  :args (s/cat ::jobs vector?))

(def workers-available? true)

(defn scheduler [jobs]
  (loop [jobs jobs
         outputs []]
    (let [{:keys [run payload]} (first jobs)]
      (if (and workers-available? (seq jobs))
        (if-some [result (run payload)]
          (recur (rest jobs) (conj outputs result)))
        outputs))))

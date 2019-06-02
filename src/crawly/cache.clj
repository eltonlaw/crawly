(ns crawly.cache
  (:require [clojure.spec.alpha :as s]))

(s/def ::level #{:none :aggressive})

(def level (atom :none))

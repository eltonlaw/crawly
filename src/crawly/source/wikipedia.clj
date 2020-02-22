(ns crawly.source.wikipedia
  (:require [crawly.source.core :as source]))

(def WikipediaSource
  (reify Source
    (setup [_])
    (cleanup [_])
    (data [_])))

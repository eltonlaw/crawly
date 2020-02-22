(ns crawly.source.core)

(defprotocol Source
  (setup [_])
  (cleanup [_])
  (data [_]))

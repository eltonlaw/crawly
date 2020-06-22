(ns crawly.core
  (:require
    [clojure.core.async :refer [go >! chan]]
    [org.httpkit.client :as client]
    [taoensso.timbre :as timbre]
    [taoensso.timbre.appenders.core :as appenders]))

(timbre/merge-config!
  {:level :debug
   :appenders {:spit (appenders/spit-appender {:fname "crawly.log"})}})

(def request-ch (chan))

(defn GET [url {:keys [timeout]}]
  (let [p (client/request {:url url})]
    (go (>! request-ch (deref p timeout {:success false})))))

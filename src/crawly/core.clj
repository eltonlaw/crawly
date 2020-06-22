(ns crawly.core
  (:require
    [clojure.core.async :refer [go >! <! chan]]
    [org.httpkit.client :as client]
    [taoensso.timbre :as timbre]
    [taoensso.timbre.appenders.core :as appenders]))

(timbre/merge-config!
  {:level :debug
   :appenders {:spit (appenders/spit-appender {:fname "crawly.log"})}})

(def job-count (atom 0))
(def response-ch (chan))

(defn GET
  "Run GET on url in thread, putting onto response channel when done"
  ([url] (GET url {}))
  ([url {:keys [timeout post-fn] :or {timeout 2000
                                      post-fn identity}}]
   (let [id (swap! job-count inc)]
     (go
       (let [p (client/request {:url url})
             response (if-let [promise-val (deref p timeout nil)]
                        promise-val
                        ;; Default value for when response times out
                        {:status 408})]
         (>! response-ch {:id id :response response})))
     id)))

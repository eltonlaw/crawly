(ns crawly.core
  (:require
    [clojure.core.async :refer [go go-loop >! <! chan]]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [org.httpkit.client :as client]
    [taoensso.timbre :as timbre]
    [taoensso.timbre.appenders.core :as appenders]))

;; Path string to file containing requests
(def ^:dynamic *requests-log-file*
  (or (System/getenv "CRAWLY_REQUESTS_LOG") "crawly.requests.log"))

;; Path string to file containing all log messages
(def ^:dynamic *log-file*
  (or (System/getenv "CRAWLY_LOG") "crawly.log"))

;; Logging initialization
(timbre/merge-config!
  {:level :debug
   :appenders {:spit (appenders/spit-appender {:fname *log-file*})}})

;; Global state shared between scraping threads
(def job-count (atom 0))
(def jobs (atom {}))
(def response-ch (chan))

(defn GET
  "Run GET on url in thread, putting onto response channel when done"
  ([url] (GET url {}))
  ([url {:keys [timeout post-fn] :or {timeout 2000
                                      post-fn identity}}]
   (let [id (swap! job-count inc)]
     ;; FIXME: This needs to be done all at once, need to put into a go-loop i think
     (spit *requests-log-file* (format "GET,%d,%s\n" id url) :append true)
     (timbre/infof "[%d] GET %s" id url)
     (go
       (let [p (client/request {:url url})
             response (if-let [promise-val (deref p timeout nil)]
                        promise-val
                        ;; Default value for when response times out
                        {:status 408})]
         (>! response-ch {:id id :response response})))
     id)))

(defn process-jobs-worker!
  "Starts a new thread which constantly takes responses off the response channel
  and assocs them to the global map of finished jobs. Running multiple versions
  of this shouldn't cause any issues."
  []
  (go-loop []
    (let [response (<! response-ch)]
      (swap! jobs-lookup assoc (:id response) response))
    (recur)))

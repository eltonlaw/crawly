(ns crawly.core
  (:require
    [clojure.core.async :refer [go >! <! chan]]
    [clojure.string :as str]
    [org.httpkit.client :as client]
    [taoensso.timbre :as timbre]
    [taoensso.timbre.appenders.core :as appenders]))

(defn- logging-output-fn
  "Simplify logging output to write csv-like data"
  [{:keys [display-errors sep]} {:keys [level ?err msg_ timestamp_]}]
  (str
    (str/replace (force timestamp_) #" " "-")
    sep
    (str/upper-case (name level))
    sep
    (force msg_)
    (when-let [err (and display-errors ?err)]
      (str "\n" (timbre/stacktrace err {})))))

(timbre/merge-config!
  {:level :debug
   :output-fn (partial logging-output-fn {:display-errors false :sep ","})
   :appenders {:spit (appenders/spit-appender {:fname "crawly.log"})}})

(def job-count (atom 0))
(def response-ch (chan))

(defn GET
  "Run GET on url in thread, putting onto response channel when done"
  ([url] (GET url {}))
  ([url {:keys [timeout post-fn] :or {timeout 2000
                                      post-fn identity}}]
   (timbre/infof "GET %s" url)
   (let [id (swap! job-count inc)]
     (go
       (let [p (client/request {:url url})
             response (if-let [promise-val (deref p timeout nil)]
                        promise-val
                        ;; Default value for when response times out
                        {:status 408})]
         (>! response-ch {:id id :response response})))
     id)))

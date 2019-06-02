(ns crawly.core
  (:require [clojure.spec.alpha :as s]
            [clj-http.client :as client]
            [crawly.cache :as cache]
            [taoensso.timbre :refer [info]]))

(defn set-cache-level!
  [level]
  (if (s/valid? ::cache/level level)
    (reset! cache/level level)
    (throw (ex-info (str "Invalid cache level " level)
                    {:causes {:level level}}))))

(s/fdef GET
  :args (s/cat ::url string?)
  :ret (s/nilable string?))

(defn GET
  [url]
  (if (and (= @cache/level :aggressive)
           (cache/cached? url))
    (cache/load-response url)
    (let [response (client/get url)]
      (if (= @cache/level :aggressive)
        (cache/add url response))
      (if (not= (:status response) 200)
        (info "Non 200 response: " response))
      response)))

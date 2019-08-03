(ns crawly.core
  (:require [clojure.spec.alpha :as s]
            [clj-http.client :as client]
            [crawly.file-cache :as file-cache]
            [crawly.validate :as validate]
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
  (let [response (or (file-cache/lookup url)
                     (client/get url))]
    (cache/add! url response)
    (validate/conform response)))

(defn -main [& args]
  (println "---- crawly/-main ------"))

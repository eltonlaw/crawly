(ns crawly.cache
  (:require [clojure.spec.alpha :as s]
            [clojure.java.io :as io]
            [taoensso.timbre :refer [debug]]))

(s/def ::path string?)
(s/def ::level #{:none :aggressive})

(def path (atom (str (System/getProperty "java.io.tmpdir")
                     "/crawly-"
                     (quot (System/currentTimeMillis) 1000))))
(def level (atom :none))

(defn- tempfile-path [url]
  (str @path "/" (hash url) ".html"))

(defn cached?
  "True if hashed html file is where #'crawly.cache/path` points"
  [url]
  (-> (tempfile-path url) io/as-file .exists))

(defn add
  "Get full file path, make parent dir if required and write html file"
  [url response]
  (debug "...adding response to cache for:" url)
  (let [fpath (tempfile-path url)]
    (io/make-parents fpath)
    (spit fpath response)))

(defn load-response
  "Reads file from cache"
  [url]
  (debug "...loading response from cache for:" url)
  (-> (tempfile-path url) slurp))

(defn delete-dir-files [fname]
  (doseq [f (-> fname io/file .listFiles)]
    (if (.isFile f)
      (io/delete-file f))))

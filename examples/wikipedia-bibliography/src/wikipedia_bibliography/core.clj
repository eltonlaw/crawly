(ns wikipedia-bibliography.core
  (:require [crawly.core :as crawly]))

(crawly/set-cache-level! :aggressive)

(defn -main [& args]
  (println (crawly/GET "https://en.wikipedia.org/wiki/User_agent")))

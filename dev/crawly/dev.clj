(ns crawly.dev
  (:require [crawly.core :refer :all]
            [crawly.test-handlers :as test-handlers]
            [org.httpkit.server :refer [run-server]]
            [taoensso.timbre :as timbre]))

(timbre/set-level! :info)

(def url "http://localhost:3000") ;; Default url
(def servers (atom {}))

(defn stop-servers!
  [& handlers]
  (doseq [handler (or (seq handlers)
                      (keys @servers))]
    (when-let [server (get @servers handler)]
      (server)
      (swap! servers dissoc handler)
      (println "...successfully stopping server -" handler))))

(defn start-server!
  ([handler] (start-server! handler {:port 3000 :join? false}))
  ([handler metadata]
   (when (get @servers handler)
     (println "...handler being replicated. To prevent hanging instance deleting old version first... ")
     (stop-servers! handler))
   (swap! servers assoc handler (run-server handler metadata))
   (when (get @servers handler)
     (println "...successfully started server -" handler))))

(defmacro with-server [bindings & body]
  (let [[server handler] bindings]
    `(let [~server (start-server! ~handler)]
      (do ~@body)
      (stop-servers! ~handler))))

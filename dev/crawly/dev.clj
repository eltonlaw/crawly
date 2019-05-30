(ns crawly.dev
  (:require [crawly.core :refer :all]
            [crawly.test-handlers :as th]
            [org.httpkit.server :refer [run-server]]))

(def url "http://localhost:3000") ;; Default url
(def servers (atom {}))

(defn start-server!
  ([handler] (start-server! handler {:port 3000 :join? false}))
  ([handler metadata]
   (swap! servers assoc handler (run-server handler metadata))))

(defn stop-server!
  [handler]
  (if-let [server (get @servers handler)]
    ;; Stop server and remove from global list
    (do (server) (swap! servers dissoc handler))
    (println "Error: Trying to stop server that isn't running for handler: " handler)))

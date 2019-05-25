(set-env!
  :resource-paths #{"src"}
  :dependencies '[[adzerk/boot-reload "0.6.0" :scope "test"]
                  [org.clojure/clojure "1.8.0"]])

(task-options!
  pom {:project 'crawly
       :version "0.1.0-SNAPSHOT"
       :description ""}
  jar {:main 'crawly.core})

(require '[crawly.core :as crawly]
         '[adzerk.boot-reload :refer [reload]])

(deftask dev []
  (comp
    (repl :no-color true :init-ns 'crawly.core)))

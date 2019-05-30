(set-env!
  :resource-paths #{"src" "dev"}
  :dependencies '[[adzerk/boot-reload "0.6.0" :scope "test"]
                  [org.clojure/clojure "1.8.0"]
                  [clj-http "3.9.1"]
                  [hiccup "1.0.5"]
                  [http-kit "2.3.0"]])

(task-options!
  pom {:project 'crawly
       :version "0.1.0-SNAPSHOT"
       :description "Web crawling framework for Clojure"}
  jar {:main 'crawly.core})

(require '[crawly.core :as crawly]
         '[adzerk.boot-reload :refer [reload]])

(deftask dev []
  (comp
    (repl :no-color true :init-ns 'crawly.dev)))
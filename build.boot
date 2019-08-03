(set-env!
  :resource-paths #{"src" "dev" "test"}
  :dependencies '[[adzerk/boot-reload "0.6.0" :scope "test"]
                  [adzerk/boot-test "1.2.0" :scope "test"]
                  [clj-http "3.9.1"]
                  [com.taoensso/timbre "4.10.0"]
                  [hiccup "1.0.5"]
                  [http-kit "2.3.0"]
                  [org.clojure/clojure "1.10.0"]
                  [org.clojure/tools.nrepl "0.2.13"]])

(task-options!
  pom {:project 'crawly
       :version "0.1.0-SNAPSHOT"
       :description "Web crawling framework for Clojure"}
  jar {:main 'crawly.core})

(require '[crawly.core :as crawly]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-test :refer :all])

(deftask dev []
  (repl :no-color true :init-ns 'crawly.dev))

(deftask local-install []
  (comp (pom) (jar) (install)))

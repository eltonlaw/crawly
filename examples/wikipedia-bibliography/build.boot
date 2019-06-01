(set-env!
  :resource-paths #{"src"}
  :checkouts '[[crawly/crawly "0.1.0-SNAPSHOT"]]
  :dependencies '[[adzerk/boot-reload "0.6.0" :scope "test"]
                  [adzerk/boot-test "1.2.0" :scope "test"]
                  [crawly/crawly "0.1.0-SNAPSHOT"]
                  [org.clojure/clojure "1.10.0"]])

(deftask dev []
  (repl :init-ns 'wikipedia-bibliography.core))
 

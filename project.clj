(defproject crawly "0.1.0-SNAPSHOT"
  :description "Web crawling framework for Clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :resource-paths #{"src" "dev"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.9.1"]
                 [hiccup "1.0.5"]
                 [http-kit "2.3.0"]]
  :repl-options {:init-ns crawly.dev})

(defproject crawly "0.1.0-SNAPSHOT"
  :description "Modular web scraping/crawling framework for Clojure"
  :url "https://github.com/eltonlaw/crawly"
  :license {:name "MIT"
            :url "https://raw.githubusercontent.com/eltonlaw/crawly/master/LICENSE"}
  :global-vars {*warn-on-reflection* true}
  :resource-paths #{"src" "dev"}
  :test-paths ["test"]
  :dependencies [[com.taoensso/timbre "4.10.0"] ;
                 [org.clojure/tools.reader"1.3.2"]
                 [etaoin "0.3.6"]
                 [hiccup "1.0.5"]
                 [http-kit "2.3.0"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "0.7.559"]
                 [org.clojure/spec.alpha "0.2.176"]]
  :repl-options {:init-ns crawly.dev}
  :profiles
  {:dev 
    {:resource-paths #{"resources" "target"}
     :dependencies [[org.clojure/clojurescript "1.10.773"]
                    [com.bhauman/figwheel-main "0.2.9"]
                    [metosin/reitit "0.5.2"]
                    [reagent "0.10.0"]
                    ;; optional but recommended
                    [com.bhauman/rebel-readline-cljs "0.1.4"]]}}
  :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]})

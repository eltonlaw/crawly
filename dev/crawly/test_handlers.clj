(ns crawly.test-handlers
  (:require [hiccup.core :as hiccup]
            [hiccup.page :as hiccup-page]))

(defn basic [request]
  (let [web-page [:body [:div "some content"]]]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (-> web-page
               hiccup-page/html5
               hiccup/html)}))

(defn links [request]
  (let [web-page [:body
                  [:a "link1"]
                  [:b "link2"]
                  [:c "link3"]]]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (-> web-page
               hiccup-page/html5
               hiccup/html)}))

(ns crawly.test-handlers
  (:require [hiccup.core :as hiccup]
            [hiccup.page :as hiccup-page]))

(def web-page
  [:body
   [:div "some content"]])

(defn basic [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (-> web-page
             hiccup-page/html5
             hiccup/html)})

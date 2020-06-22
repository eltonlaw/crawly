(ns crawly.webdriver
  (:require
    [etaoin.api :refer :all]
    [etaoin.keys :as k]
    [clojure.java.io :as io]
    [taoensso.timbre :as timbre]))

(defonce ^:dynamic *default-driver-settings*
  {:prefs {:browser.download.dir "./crawly-downloads"
           :browser.helperApps.neverAsk.saveToDisk "text/plain,text/csv,image/jpeg,text/xml,application/octet-stream,application/excel,application/vnd.ms-excel,application/vnd.msexcel"
           :browser.download.folderList 2
           :browser.download.manager.showWhenStarting false
           :pdfjs.disabled true}})

(defn create-folder! [path]
  (let [f (io/file path)]
    (if-not (and (.exists f) (.isDirectory f))
      (.mkdir f)
      (timbre/debugf "Creating folder %s...already exists" path))))

(defn new-driver []
  (create-folder! (get-in *default-driver-settings* [:prefs :browser.download.dir]))
  (chrome *default-driver-settings*))

(ns lt.plugins.emacsification
  (:require [lt.object :as object]
            [lt.objs.tabs :as tabs]
            [lt.objs.files :as files]
            [lt.objs.sidebar.workspace :as workspace]
            [lt.objs.command :as cmd])
  (:require-macros [lt.macros :refer [defui behavior]]))

(defn refresh-directory [fname]
  (let [directories (->@workspace/tree :folders)
        dir (first (filter #(= (-> % deref :path files/basename) fname) directories))]
    (object/raise dir :refresh!)))

(ns lt.plugins.emacsification.recenter
  (:require [lt.object :as object]
            [lt.objs.command :as cmd]
            [lt.objs.editor :as editor]
            [lt.objs.editor.pool :as pool]))

(def recenter-obj (object/create (object/object* ::recenter-obj :pos nil :scroll [])))

(defn recenter []
  (let [ed (pool/last-active)
        pos (editor/->cursor ed)]
    (when-not (= pos (:pos @recenter-obj))
      (let [height (-> ed editor/->cm-ed .getScrollInfo .-clientHeight)
            cur (.-top (.charCoords (editor/->cm-ed ed) (clj->js {:line (:line pos) :ch 0}) "local"))]
        (object/merge! recenter-obj {:pos pos
                                     :scroll [(- cur (* 0.5 height))
                                              (- cur (* 0.1 height))
                                              (- cur (* 0.9 height))]})))
    (editor/scroll-to ed 0 (->> @recenter-obj :scroll first))
    (object/update! recenter-obj [:scroll] (fn [[a b c]] [b c a]))))

(cmd/command {:command :emacsification.recenter
              :desc "Emacsification: Recenter"
              :exec recenter})

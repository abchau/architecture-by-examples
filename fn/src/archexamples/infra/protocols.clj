(ns archexamples.infra.protocols)


(defprotocol Renderer

  (render [context view-name values]))

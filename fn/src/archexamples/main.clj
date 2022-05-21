(ns archexamples.main
  (:require
    [archexamples.infra :as infra]
    [archexamples.infra.web :as web]
    [ring.adapter.jetty :refer [run-jetty]]))


(defn -main
  [& args]
  (let [handler (-> (infra/->context args)
                    (web/make-handler))
        options (merge {:port 8080
                        :async? true}
                       args)]
    (run-jetty handler options)))


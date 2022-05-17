(ns archexamples.adapter.handler
  "Adapt domain to website type of frontend"
  (:require
    [archexamples.infra.protocols :as p]
    [archexamples.logic.subscription :as s])
  (:import
    (clojure.lang
      ExceptionInfo)))


(defn render-html
  "Render and construct a 200 response"
  [context template vars]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (p/render context template vars)})


(defn subscribe-index-handler
  [{:keys [context] :as req} respond raise]
  (respond (render-html context "subscribe" nil)))


(defn subscribe-handler
  [{:keys [context] :as req} respond raise]
  (let [email (-> req :form-params (get "email"))
        result (try (s/subscribe context email)
                    (catch ExceptionInfo ex
                      (respond (render-html context "subscribe" (ex-data ex)))))]
    (respond (render-html context "subscribe" result))))

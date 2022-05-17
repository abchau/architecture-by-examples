(ns archexamples.infra.web
  (:require
    [archexamples.adapter.handler :as h]
    [clojure.tools.logging :as log]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:import (archexamples.infra ApplicationContext)))


(defn route-handler
  "Central place for routing"
  [{:keys [request-method uri] :as req} respond raise]
  (case [request-method uri]
    [:get "/subscribe"]
    (h/subscribe-index-handler req respond raise)
    [:post "/subscribe"]
    (h/subscribe-handler req respond raise)
    (respond {:status 404 :body "Not Found"})))


(defn wrap-context
  "Inject application context to request"
  [h ^ApplicationContext context]
  (fn [req respond raise]
    (h (assoc req :context context)
       respond
       raise)))


(defn wrap-exception-handling
  "Add Global Exception handling"
  [h]
  (fn [req respond raise]
    (try
      (h req respond (fn [ex]
                       (log/error ex "Uncaught Exception throw inside the chain")
                       (respond {:status 500 :body "Internal Server Error"})))
      (catch Throwable ex
        (log/error ex "Uncaught Exception from outside the chain")
        (respond {:status 500 :body "Internal Server Error"})))))


(defn make-handler
  "Combine all middlewares and handler to a single function"
  [context]
  (-> route-handler
      (wrap-context context)
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (wrap-exception-handling)))

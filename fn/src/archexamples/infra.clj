(ns archexamples.infra
  (:require
    [archexamples.infra.protocols :as p]
    [archexamples.logic.subscription :as s]
    [archexamples.thymeleaf :as thymeleaf]))


(defrecord ApplicationContext
  [db renderer])


(extend ApplicationContext
  s/SubscriptionRepository
  {:save! (fn [{:keys [db]} email]
            (swap! db conj email))
   :exists? (fn [{:keys [db]} email]
              (contains? @db email))}
  p/Renderer
  {:render (fn [{:keys [renderer]} template-name vars]
             (thymeleaf/render renderer template-name vars))})


(defn ->context
  [& opts]
  (->ApplicationContext (atom #{})
                        (thymeleaf/->template-engine)))

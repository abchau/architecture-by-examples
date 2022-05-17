(ns archexamples.logic.subscription
  "Subscription related logic"
  (:require
    [archexamples.logic.specs :refer [EmailAddress]]
    [malli.core :as m]))


(defprotocol SubscriptionRepository

  (save!
    [context email]
    "Save subscription")

  (exists?
    [context email]
    "Did subscription already exists in system"))


(defn subscribe
  "Subscribe to our service.
  Throw exception when email is not correct or already exists."
  [context email]
  (when-not (m/validate EmailAddress email)
    (throw (ex-info "email.format"
                    {:email email :message "email.format"})))
  (when (exists? context email)
    (throw (ex-info
             "email.duplicate"
             {:email email :message "email.duplicate"})))
  (save! context email)
  {:email email :message "success"})

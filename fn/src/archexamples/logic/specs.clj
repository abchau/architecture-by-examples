(ns archexamples.logic.specs
  (:require
    [malli.core :as m]
    [malli.error :as me]))


(def EmailAddress
  [:and
   [:string {:min 1 :max 512 :error/message "email.empty"}]
   [:re {:error/message "email.format"}
    #".*@.*\..*"]])


(comment
 (-> (m/explain EmailAddress
                "")
     (me/humanize)))

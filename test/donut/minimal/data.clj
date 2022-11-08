(ns donut.minimal.data
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as sg]
            [donut.datapotato.next-jdbc :as dfn]
            [donut.endpoint.test.harness :as deth]
            [donut.minimal.backend.system] ;; for multimethod
            [next.jdbc :as jdbc]))

(defn db-connection
  []
  (deth/instance [:db :connection]))

(s/def :user/username string?)

(s/def :user/entity
  (s/keys :req-un [:user/username]))

(def dd-schema
  {:user      {:prefix    :u
               :generate  {:schema :user/entity}
               :fixtures  {:table-name "user"}}})

(def datapotato-db
  {:schema   dd-schema
   :generate {:generator (comp sg/generate s/gen)}
   :fixtures (merge dfn/config
                    {:get-connection (fn [_] (db-connection))
                     :setup          (fn [{{:keys [connection]} :fixtures}]
                                       (jdbc/execute! connection ["TRUNCATE TABLE user CASCADE"]))})})

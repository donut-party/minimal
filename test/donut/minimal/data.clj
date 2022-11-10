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

(def potato-schema
  {:example-entity {:prefix   :ee
                    :generate {:schema [:map]}
                    :fixtures {:table-name "example_entity"}}})

(def datapotato-db
  {:schema   potato-schema
   :generate {:generator (comp sg/generate s/gen)}
   :fixtures (merge dfn/config
                    {:get-connection (fn [_] (db-connection))
                     :setup          (fn [{{:keys [connection]} :fixtures}]
                                       (comment
                                         (jdbc/execute! connection ["TRUNCATE TABLE example_entity CASCADE"])))})})

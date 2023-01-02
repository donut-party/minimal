(ns donut.minimal.backend.endpoint.example-entity-endpoint
  (:require
   [donut.minimal.backend.query.example-entity :as qe]
   [next.jdbc.sql :as jsql]))

(def collection-handlers
  {:get
   {:handler
    (fn [{:keys [dependencies]}]
      {:status 200
       :body   (qe/entities (:datasource dependencies))})}

   :post
   {:handler
    (fn [{:keys [all-params dependencies]}]
      {:status 200
       :body   (jsql/insert! (:datasource dependencies) :entity all-params)})}})

(def member-parameters
  {:path [:map
          [:example_entity/id int?]]})

(def member-handlers
  {:get
   {:parameters member-parameters
    :handler    (fn [{:keys [all-params dependencies]}]
                  {:status 200
                   :body   (qe/entity-by-id (:datasource dependencies) all-params)})}

   :put
   {:parameters member-parameters
    :handler    (fn [{:keys [all-params dependencies]}]
                  (jsql/update! (:datasource dependencies)
                                :entity
                                (dissoc all-params :entity/id)
                                (select-keys all-params [:entity/id]))
                  {:status 200
                   :body   (qe/entity-by-id (:datasource dependencies) all-params)})}

   :delete
   {:parameters member-parameters
    :handler    (fn [{:keys [all-params dependencies]}]
                  (jsql/delete! (:datasource dependencies) :entity (select-keys all-params [:entity/id])))}})

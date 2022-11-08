(ns donut.minimal.backend.endpoint.user
  (:require
   [donut.minimal.backend.query.user :as qu]
   [next.jdbc.sql :as jsql]))

(def collection-parameters
  {:path [:map [:todo_list/id int?]]})

(def collection-handlers
  {:get
   {:parameters collection-parameters
    :handler
    (fn [{:keys [db]}]
      {:status 200
       :body   (qu/users db)})}

   :post
   {:parameters collection-parameters
    :handler
    (fn [{:keys [all-params db]}]
      {:status 200
       :body   (jsql/insert! db :user all-params)})}})

(def member-parameters
  {:path [:map
          [:user/id int?]]})

(def member-handlers
  {:get
   {:parameters member-parameters
    :handler    (fn [{:keys [all-params db]}]
                  {:status 200
                   :body   (qu/user-by-id db all-params)})}

   :put
   {:parameters member-parameters
    :handler    (fn [{:keys [all-params db]}]
                  (jsql/update! db
                                :user
                                (dissoc all-params :user/id)
                                (select-keys all-params [:user/id]))
                  {:status 200
                   :body   (qu/user-by-id db all-params)})}

   :delete
   {:parameters member-parameters
    :handler    (fn [{:keys [all-params db]}]
                  (jsql/delete! db :user (select-keys all-params [:user/id])))}})

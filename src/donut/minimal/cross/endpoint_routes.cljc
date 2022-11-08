(ns donut.minimal.cross.endpoint-routes
  (:require [donut.routes :as dr]
            #?@(:clj
                [[donut.minimal.backend.endpoint.user :as user]])))

(def routes
  (dr/merge-route-opts
   [["/api/v1/users"
     {:name     :users
      :ent-type :user
      :id-key   :user/id}
     #?(:clj user/collection-handlers)]
    ["/api/v1/users/{:user/id}"
     {:name     :user
      :ent-type :user
      :id-key   :user/id}
     #?(:clj user/member-handlers)]]))

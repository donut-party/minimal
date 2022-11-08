(ns donut.minimal.frontend.frontend-routes
  (:require
   [reitit.coercion.malli :as rm]
   [donut.frontend.form.flow :as dff]
   [donut.frontend.sync.flow :as dsf]
   [donut.minimal.frontend.components.home :as h]
   [donut.minimal.frontend.components.user :as u]))

(def routes
  [["/"
    {:name       :home
     :components {:main [h/component]}
     :lifecycle  {:enter [[::dsf/get :todo-lists]]}}]
   ["/users/{user/id}"
    {:name       :user
     :components {:main [u/show]}
     :coercion   rm/coercion
     :lifecycle  {:enter [[::dff/clear {:donut.form/key [:post :users]}]
                          [::dsf/get :user {::dsf/rules #{::dsf/merge-route-params}}]]}
     :parameters {:path [:map [:user/id int?]]}}]])

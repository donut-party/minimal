(ns donut.minimal.frontend.subs
  (:require
   [donut.frontend.core.utils :as dcu]
   [donut.frontend.nav.utils :as dnu]
   [re-frame.core :as rf]))

(rf/reg-sub :users
  (fn [db]
    (dcu/entities db :user :user/id)))

(rf/reg-sub :routed-user
  (fn [db]
    (dnu/routed-entity db :user :user/id)))

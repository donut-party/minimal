(ns donut.minimal.frontend.handlers
  (:require
   [donut.frontend.core.utils :as dcu]
   [donut.frontend.nav.flow :as dnf]
   [donut.frontend.sync.flow :as dsf]
   [re-frame.core :as rf]))

(rf/reg-event-fx :delete-user
  [rf/trim-v]
  (fn [{:keys [db]} [user]]
    {:db (dcu/dissoc-entity db :user (:user/id user))
     :fx [[:dispatch [::dsf/delete :user {:route-params user
                                          :on           {:success [::dnf/navigate-route :home]}}]]]}))

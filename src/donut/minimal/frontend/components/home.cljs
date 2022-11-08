(ns donut.minimal.frontend.components.home
  (:require
   [donut.frontend.core.utils :as dcu]
   [donut.frontend.form.components :as dfc]
   [donut.frontend.form.flow :as dff]
   [donut.frontend.nav.components :as dnc]
   [donut.minimal.frontend.ui :as ui]
   [re-frame.core :as rf]))

(defn todo-list-li
  [todo-list]
  ;; TODO add key
  [ui/link
   (dnc/simple-route-link
    {:route-name   :todo-list
     :route-params todo-list}
    (:todo_list/title todo-list))])

(defn component
  []
  [:div
   [:div {:class "mb-8"}
    [ui/h2 "New User"]
    (dfc/with-form [:post :todo-user]
      [ui/form
       [:form {:on-submit (dcu/prevent-default #(*submit {:on {:success [[::dff/clear-form :$ctx]]}}))}
        [(dcu/focus-component [*field :text :username {:placeholder           "username"
                                                       :donut.field/no-label? true}])]]])]
   [:div
    [ui/h2 "Users"]
    (let [todo-lists @(rf/subscribe [:users])]
      [ui/ul (map todo-list-li todo-lists)])]])

(ns donut.minimal.backend.endpoint.user-test
  (:require
   [clojure.test :refer [deftest is use-fixtures]]
   [donut.datapotato.core :as dc]
   [donut.endpoint.test.harness :as deth]
   [donut.minimal.data :as data]))

(use-fixtures :each (deth/system-fixture [:test]))

(deftest gets-users
  (dc/with-fixtures data/datapotato-db
    (let [{:keys [u0 u1]} (dc/insert-fixtures {:user [[2]]})]
      (is (= [u0 u1]
             (deth/read-body (deth/handle-request :get :users)))))))

(deftest creates-user
  (is (deth/contains-entity? (deth/read-body (deth/handle-request :post :users {:username "test"}))
                             #:user{:username "test"})))

(deftest gets-single-user
  (dc/with-fixtures data/datapotato-db
    (let [{:keys [u0]} (dc/insert-fixtures {:user [[2]]})]
      (is (= [[:entities [:user :user/id [u0]]]]
             (deth/read-body (deth/handle-request :get [:user (select-keys u0 [:user/id])])))))))

(deftest updates-user
  (dc/with-fixtures data/datapotato-db
    (let [{:keys [u0]} (dc/insert-fixtures {:user [[1]]})]
      (is (= #:user{:id       (:todo_list/id u0)
                    :username "new title"}
             (-> (deth/handle-request
                  :put
                  [:user {:user/id (:user/id u0)}]
                  {:title "new title"})
                 (deth/read-body)))))))

(deftest deletes-user
  (dc/with-fixtures data/datapotato-db
    (let [{:keys [u0]} (dc/insert-fixtures {:user [[1]]})]
      (deth/handle-request :delete [:user (select-keys u0 [:todo_list/id])])
      (is (= []
             (deth/read-body (deth/handle-request :get :users)))))))

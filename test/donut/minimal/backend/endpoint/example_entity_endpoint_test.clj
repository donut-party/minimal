(ns donut.minimal.backend.endpoint.example-entity-endpoint-test
  (:require
   [clojure.test :refer [deftest is use-fixtures]]
   [donut.datapotato.core :as dc]
   [donut.endpoint.test.harness :as deth]
   [donut.minimal.fixtures :as fixtures]
   [donut.system :as ds]))

(use-fixtures :each (ds/system-fixture :test))

;; example tests for a hypothetical example-entity:
(deftest gets-example-entities
  (dc/with-fixtures fixtures/datapotato-db
    (let [{:keys [ee0 ee1]} (dc/insert-fixtures {:example-entity [[2]]})]
      (is (= (sort-by :example_entity/id [ee0 ee1])
             (sort-by :example_entity/id (deth/read-body (deth/handle-request :get :example-entities))))))))

(deftest creates-example-entity
  (is (deth/contains-entity? (deth/read-body (deth/handle-request :post :example-entities {:description "test"}))
                             #:example_entity{:id          nil
                                              :description "test"})))

(deftest gets-single-example-entity
  (dc/with-fixtures fixtures/datapotato-db
    (let [{:keys [u0]} (dc/insert-fixtures {:example-entity [[2]]})]
      (is (= [[:entities [:example-entity :example-entity/id [u0]]]]
             (deth/read-body (deth/handle-request :get [:example-entity (select-keys u0 [:example-entity/id])])))))))

(deftest updates-example-entity
  (dc/with-fixtures fixtures/datapotato-db
    (let [{:keys [u0]} (dc/insert-fixtures {:example-entity [[1]]})]
      (is (= #:example-entity{:id       (:example-entity/id u0)
                              :example-entityname "new-example-entity-name"}
             (-> (deth/handle-request
                  :put
                  [:example-entity (select-keys u0 [:example-entity/id])]
                  {:example-entityname "new-example-entity-name"})
                 (deth/read-body)))))))

(deftest deletes-example-entity
  (dc/with-fixtures fixtures/datapotato-db
    (let [{:keys [u0]} (dc/insert-fixtures {:example-entity [[1]]})]
      (deth/handle-request :delete [:example-entity (select-keys u0 [:example-entity/id])])
      (is (= []
             (deth/read-body (deth/handle-request :get :example-entities)))))))

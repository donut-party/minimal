(ns donut.minimal.backend.query.user
  (:require [honey.sql :as sql]
            [next.jdbc.sql :as jsql]))

(defn users
  [db]
  (->> {:select [:*]
        :from   [:user]}
       sql/format
       (jsql/query db)
       (into [])))

(defn user-by-id
  [db {:keys [:user/id]}]
  (->> {:select [:*]
        :from   [:user]
        :where  [:= :user/id id]}
       sql/format
       (jsql/query db)
       first))

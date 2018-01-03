(ns emmerce.handlers
  (:require [emmerce.db :as db]
            [re-frame.core :refer [dispatch reg-event-db]]))

(reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

(reg-event-db
 :set-active-page
 (fn [db [_ page]]
   (assoc db :page page)))

(reg-event-db
 :set-active-user
 (fn [db [_ user email]]
   (assoc db :user {:name user :email email})))

(reg-event-db
 :set-docs
 (fn [db [_ docs]]
   (assoc db :docs docs)))

(reg-event-db
 :inc-level
 (fn [db [_ lvl]]
   (assoc-in db [:chatbot :level] (inc lvl))))

(reg-event-db
 :dec-level
 (fn [db [_ lvl]]
   (assoc-in db [:chatbot :level] (dec lvl))))

(reg-event-db
 :toggle-completed
 (fn [db [_ lvl]]
   (assoc-in db [:chatbot lvl :completed] true)))

(reg-event-db
 :toggle-active
 (fn [db [_ lvl]]
   (assoc-in db [:chatbot lvl :active] true)))


(reg-event-db
 :set-chats
 (fn [db [_ chats]]
   (assoc db :chats chats)))

(reg-event-db
 :set-fruits
 (fn [db [_ fruits]]
   #_(println fruits)
   (assoc db :fruits fruits)))

(reg-event-db
 :set-snackbar
 (fn [db [_ status msg]]
   (assoc db :snackbar {:status  status :msg msg})))

(ns emmerce.subscriptions
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :page
  (fn [db _]
    (:page db)))

(reg-sub
  :docs
  (fn [db _]
    (:docs db)))

(reg-sub
 :get-user
 (fn [db _]
   (:user db)))

(reg-sub
 :get-level
 (fn [db _]
   (:level (:chatbot db))))

(reg-sub
 :get-completed-status
 (fn [db [_ lvl]]
   (:completed (lvl (:chatbot db)))))

(reg-sub
 :get-active-status
 (fn [db [_ lvl]]
   (:active (lvl (:chatbot db)))))

(reg-sub
 :get-video-id
 (fn [db [_ lvl]]
   (:id (lvl (:chatbot db)))))

(reg-sub
 :get-user-email
 (fn [db _]
   (:email (:user db))))

(reg-sub
 :get-chats
 (fn [db _]
   (:chats db)))

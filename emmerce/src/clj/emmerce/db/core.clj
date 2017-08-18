(ns emmerce.db.core
    (:require [monger.core :as mg]
              [monger.collection :as mc]
              [monger.operators :refer :all]
              [mount.core :refer [defstate]]
              [monger.result :refer [acknowledged?]]
              [emmerce.config :refer [env]]))

(defstate db*
  :start (-> env :database-url mg/connect-via-uri)
  :stop (-> db* :conn mg/disconnect))

(defstate db
  :start (:db db*))

(defn create-user [email name image]
  (mc/insert db "users" {:email email
                         :name name
                         :image image}))

#_(defn update-user [email]
  (mc/update db "users" {:_id id}
             {$set {:name name
                    :email email}}))

(defn get-user [email]
  (mc/find-one-as-map db "users" {:email email}))


(defn insert-chat
  "Inserts the chats in DB"
  [email original translated]
  (acknowledged? (mc/insert db "chats" {:email email
                                        :original original
                                        :translated translated})))

(defn get-chats
  "Retrieves all the chats of the user"
  [email]
  (mc/find-maps db "chats" {:email email}))

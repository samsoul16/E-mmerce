(ns emmerce.db.core
    (:require [monger.core :as mg]
              [monger.collection :as mc]
              [monger.operators :refer :all]
              [mount.core :refer [defstate]]
              [monger.result :refer [acknowledged?]]
              [emmerce.config :refer [env]]
              [emmerce.chatbot.translator :as translator]))

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

;;;;;CHAT-BOT FUNCTIONS

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

;;;;;VIDEO-REF

(defn set-initial-level
  "Sets initial level to 1"
  [email]
  (acknowledged? (mc/insert db "videos" {:email email
                                         :level 1})))

(defn update-level
  "Updates level of user"
  [email current-level]
  (acknowledged? (mc/update db "videos" {:email email}
                            {$set {:level current-level}})))

(defn get-level
  "Retrieves the current level"
  [email]
  (mc/find-maps db "videos" {:email email}))


;;;;;;;;;;;;;GAME

;;;;;USED TO SEED FRUITS DB - HAVE TAKEN SPANISH AS DEFAULT LANGUAGE
(def f ["manzana" "albaricoque" "aguacate" "plátano" "mora" "cantalupo" "cereza" "coco" "uva" "pomelo" "kiwi" "limón" "mango" "naranja" "papaya" "melocotón" "pera" "piña" "fresa" "sandía"])

(defn add-fruit [name trans imglink]
  (mc/insert db "fruits" {:name name
                          :trans trans
                          :img imglink}))
(defn fill-fruits []
  (for [i (range 20)]
    (add-fruit (get f i)
               (translator/translate "SPANISH" (get f i))
               (str (translator/translate "SPANISH" (get f i)) ".jpg"))))

(fill-fruits)

(defn get-fruits []
  (mc/find-maps db "fruits"))

(ns emmerce.views.chatbot
  (:require [emmerce.db.core :as db]
            [emmerce.chatbot.translator :as translator]))


(defn get-result
  "Retrieves all the chats"
  [email]
  {:status true
   :content (map #(select-keys % [:original :translated]) (db/get-chats email))})

(defn get-error-result
  []
  {:status false
   :content nil})


(defn translate-text
  "Translated the original text"
  [lang text]
  (translator/translate lang text))

(defn insert-chat
  "Inserts the chat into DB"
  [email lang text]
  (if  (db/insert-chat email text  (translate-text lang text))
    (get-result email)
    (get-error-result)))

(ns emmerce.routes.home
  (:require [emmerce.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [emmerce.views.chatbot :as chatbot]
            [clojure.data.json :as json]))

(defn home-page []
  (layout/render "home.html"))

(defn insert-chat
  [email lang task]
  (layout/render-json (chatbot/insert-chat email lang task)))

(defn get-chats
  [email]
  (layout/render-json (chatbot/get-result email)))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8")))

  (POST "/addchat" [email lang text]
        (insert-chat email lang text))

  (GET "/chats" [email]
       (get-chats email)))

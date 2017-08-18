(ns emmerce.routes.home
  (:require [emmerce.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [emmerce.views.chatbot :as chatbot]
            [emmerce.views.videoref :as videoref]
            [clojure.data.json :as json]))

(defn home-page []
  (layout/render "home.html"))

;;;;;CHATBOT FUNCTIONS
(defn insert-chat
  [email lang task]
  (layout/render-json (chatbot/insert-chat email lang task)))

(defn get-chats
  [email]
  (layout/render-json (chatbot/get-result email)))

;;;;;VIDEOREF FUNCTIONS

(defn update-level
  [email level]
  (layout/render-json (videoref/update-level email level)))

(defn get-level
  [email]
  (layout/render-json (videoref/get-result email)))

(defn set-initial-level
  [email]
  (layout/render-json (videoref/set-initial-level email)))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8")))

  (GET "/addchat" [email lang text]
        (insert-chat email lang text))

  (GET "/chats" [email]
       (get-chats email))

  (GET "/updatelevel" [email level]
        (update-level email level))

  (GET "/level" [email]
       (get-level email))

  (GET "/setlevel" [email]
       (set-initial-level email)))

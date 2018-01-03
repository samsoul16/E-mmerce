(ns emmerce.views.game
  (:require [emmerce.db.core :as db]
            [emmerce.chatbot.translator :as translator]))


(defn get-fruits  []
  {:fruits (mapv #(select-keys % [:name :trans :img]) (db/get-fruits))})

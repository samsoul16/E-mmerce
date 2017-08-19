(ns emmerce.pages.game
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [emmerce.ajax :refer [load-interceptors!]]
            [emmerce.handlers]
            [emmerce.subscriptions]
            [reagent-material-ui.core :as ui]))

(def server "http://localhost:3000/" )

(defn game []
  #_(GET (str server "getfruits")
       {:params {}
        :format :json
        :response-format :json
        :keywords? true
        :handler recieve-chat
        :error-handler error-handler})
  [:div.game
[:h2 "GAME PAGE"]
   #_(doall (map (fn [x] [ui/Paper {:zDepth 3 :className "game-paper"}
                        [:h2 "GAME PAGE"]
                        ])
               )
          )])

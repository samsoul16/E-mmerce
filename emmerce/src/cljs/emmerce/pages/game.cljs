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

(defn log  [& msgs]
  (.log js/console (apply str msgs)))

(defn recieve-fruits [response]
  (rf/dispatch [:set-fruits (:fruits response)]))

(defn error-handler [response]
  (log "ERRORS >>>>>" response))

(defn game-quiz []
  (GET (str server "getfruits")
       {:params {}
        :format :json
        :response-format :json
        :keywords? true
        :handler recieve-fruits
        :error-handler error-handler})
  (let [idx (r/atom 0)
        fruits (rf/subscribe [:get-fruits])
        handle-click (fn [msg] (rf/dispatch [:set-snackbar true msg]))]
    (fn []
      (log "FRUITS FROM SUBSCRIPTION : " @fruits)
      (if (empty? @fruits)
        [:div "NO FRUITS LOADED"]
        (let [current (nth @fruits @idx)
              fruit3 (shuffle (cons current
                                    (take 2
                                          (shuffle (remove
                                                    (fn [x] (= (:name x)
                                                               (:name current)))
                                                    @fruits)))))]
          [:div {:className "game"}
           [ui/Paper {:zDepth 3 :className "game-paper"}
            [:h1 "Identify the Fruit"]
            [:h1 {:className "fruit-name"} (:trans current)]
            [:div {:className "fruit-holder"}
             [ui/Card {:className "fruit"}
              [:img {:className "fruit-img"
                     :src (str js/context (str "/img/game/" (:img (first fruit3))))
                     :onClick #(handle-click (if (= (:name (first fruit3))
                                                    (:name current))
                                               "Right Ans."
                                               "Wrong Ans."))}]]
             [ui/Card {:className "fruit"}
              [:img {:className "fruit-img"
                     :src (str js/context (str "/img/game/" (:img (second fruit3))))
                     :onClick #(handle-click (if (= (:name (second fruit3))
                                                    (:name current))
                                               "Right Ans."
                                               "Wrong Ans."))}]]
             [ui/Card {:className "fruit"}
              [:img {:className "fruit-img"
                     :src (str js/context (str "/img/game/" (:img (last fruit3))))
                     :onClick #(handle-click (if (= (:name (last fruit3))
                                                    (:name current))
                                               "Right Ans."
                                               "Wrong Ans."))}]]]
            [:div {:className "qs-changer"}
             [ui/RaisedButton {:label "Previous" :labelStyle {:font-size 22} :primary true
                               :style {:margin 12 :width 160 :height 60}
                               :onClick #(when (> @idx 0) ( swap! idx dec))}]
             [ui/RaisedButton {:label "Next" :labelStyle {:font-size 22} :secondary true
                               :style {:margin 12 :width 160 :height 60}
                               :onClick #(when (< @idx 19) (swap! idx inc))}]]]])))))

(defn game []
  [:div
   [game-quiz]
   [ui/Snackbar {:open (:status @(rf/subscribe [:get-snackbar]))
                 :message (:msg @(rf/subscribe [:get-snackbar]))
                 :autoHideDuration 2000 :onRequestClose
                 #(rf/dispatch [:set-snackbar false "nothing"])}]])

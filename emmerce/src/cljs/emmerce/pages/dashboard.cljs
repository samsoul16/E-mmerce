(ns emmerce.pages.dashboard
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [emmerce.ajax :refer [load-interceptors!]]
            [emmerce.handlers]
            [emmerce.subscriptions]
            [reagent-material-ui.core :as ui]))


(defn dashboard []
  [:div.centerFlex.game
   [ui/Paper {:zDepth 3}
    [:iframe.gamestyle
     {:src "https://www.gamestolearnenglish.com/htmlGames/fastEnglish/v12.1/index.html#site/personal"
      :scrolling "no"
      :height "550px"
      :width "100%"}]]])

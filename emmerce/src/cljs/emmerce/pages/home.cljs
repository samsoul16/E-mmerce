(ns emmerce.pages.home
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [emmerce.ajax :refer [load-interceptors!]]
            [emmerce.handlers]
            [emmerce.subscriptions]
            [reagent-material-ui.core :as ui]))


;; Material UI HELPERS
(def reactify r/as-element)
(defn icon [nme] [ui/FontIcon {:className "material-icons"} nme])
(defn color [name] (aget ui/colors name))

(defn home []
  [:div.home
   [ui/Paper {:className "home-card" :zDepth 4}
    [ui/Card
     [ui/CardMedia {:overlay (reactify [ui/CardTitle {:title "Whats That Called in English?" :titleStyle {:text-align "center"}}])}
      [:img {:height "750px" :src (str js/context "/img/evelyn.jpg")}]]
     [ui/CardActions
      [ui/RaisedButton {:primary true :fullWidth true :style {:height "40px"}
                        :label "Wanna Know? Come" :labelStyle {:font-size "25px"}
                        :onClick #(rf/dispatch [:set-active-page :chatbot])}] ]]
    ]
   [ui/Paper {:className "home-card" :zDepth 4}
    [ui/Card
     [ui/CardMedia {:overlay (reactify [ui/CardTitle {:title "Learn English With Our Tutor" :titleStyle {:text-align "center"}}])}
      [:img {:height "750px" :src (str js/context "/img/evelyn.jpg")}]]
     [ui/CardActions
      [ui/RaisedButton {:primary true :fullWidth true :style {:height "40px"}
                        :label "Start The Course" :labelStyle {:font-size "25px"}}] ]]
    ]])

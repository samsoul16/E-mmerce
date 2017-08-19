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
      [:img {:height "650px" :src (str js/context "/img/evelyn.jpg")}]]
     [ui/CardActions
      [ui/RaisedButton {:primary true :fullWidth true :style {:height "40px"}
                        :label "Type to Learn" :labelStyle {:font-size "25px"}
                        :onClick #(rf/dispatch [:set-active-page :chatbot])}]]]]
   [ui/Paper {:className "home-card" :zDepth 4}
    [ui/Card
     [ui/CardMedia {:overlay (reactify [ui/CardTitle {:title "Learn English With Our Tutor" :titleStyle {:text-align "center"}}])}
      [:img {:height "650px" :src (str js/context "/img/video.png")}]]
     [ui/CardActions
      [ui/RaisedButton {:primary true :fullWidth true :style {:height "40px"}
                        :label "Start The Course" :labelStyle {:font-size "25px"}
                        :onClick #(rf/dispatch [:set-active-page :videos])}]]]]
   [ui/Paper {:className "home-card" :zDepth 4}
    [ui/Card
     [ui/CardMedia {:overlay (reactify [ui/CardTitle {:title "Fast English Game" :titleStyle {:text-align "center"}}])}
      [:img {:height "650px" :src (str js/context "/img/fast.png")}]]
     [ui/CardActions
      [ui/RaisedButton {:primary true :fullWidth true :style {:height "40px"}
                        :label "Lets Play" :labelStyle {:font-size "25px"}
                        :onClick #(rf/dispatch [:set-active-page :dashboard])}]]]]])

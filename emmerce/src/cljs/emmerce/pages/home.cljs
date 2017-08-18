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
   [ui/Card {:className "home-card"}
    [ui/CardMedia {:overlay (reactify [ui/CardTitle {:title "Come Talk with Me?"}])}
     [:img {:src (str js/context "/img/evelyn.jpg")}]]]
   ])

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
  [:div
   [:h2 "DASHBOARD PAGE"]])

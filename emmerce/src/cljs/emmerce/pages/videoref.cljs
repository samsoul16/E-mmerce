(ns emmerce.pages.videoref
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [emmerce.ajax :refer [load-interceptors!]]
            [emmerce.handlers]
            [emmerce.subscriptions]
            [reagent-material-ui.core :as ui]
            [soda-ash.core :as sa]))


(defn videoref []
  []
  [:div.centerFlex
   [:div.middle
    [sa/StepGroup
     [sa/Step {:active true
               :completed true}
      [sa/Icon {:name "puzzle"}]
      [sa/StepContent
       [sa/StepTitle "Level 1"]]]
     [sa/Step {:active true
               :completed true}
      [sa/Icon {:name "puzzle"}]
      [sa/StepContent
       [sa/StepTitle "Level 2"]]]
     [sa/Step {:active true
               :completed true}
      [sa/Icon {:name "puzzle"}]
      [sa/StepContent
       [sa/StepTitle "Level 3"]]]
     [sa/Step {:active true
               :completed true}
      [sa/Icon {:name "puzzle"}]
      [sa/StepContent
       [sa/StepTitle "Level 4"]]]]]
   [:div.row
    [sa/Embed {:id "O6Xo21L0ybE"
               :source "youtube"
               :className "video"}]]
   [:div.flexButton
    [sa/Button {:content "Prev"
                :icon "left arrow"
                :labelPosition "left"}]
    [sa/Button {:content "Next"
                :icon "right arrow"
                :labelPosition "right"}]]])

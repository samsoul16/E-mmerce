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

(defn prev-operation []
  (let [lvl @(rf/subscribe [:get-level])]
    (rf/dispatch [:dec-level lvl])))


(defn next-operation []
  (let [lvl @(rf/subscribe [:get-level])]
    (if (< lvl 4)
      (do
        (rf/dispatch [:toggle-completed (keyword (str lvl))])
        (rf/dispatch [:toggle-active (keyword (str (inc lvl)))])
        (rf/dispatch [:inc-level lvl]))
      (do
        (rf/dispatch [:toggle-completed (keyword (str lvl))])
        (rf/dispatch [:toggle-active (keyword (str (inc lvl)))])
        (js/alert "Congratulations!! You have finished all the levels")))))

(defn videoref []
  []
  [:div.centerFlex
   [:div.middle
    [sa/StepGroup
     [sa/Step {:active @(rf/subscribe [:get-active-status :1])
               :completed @(rf/subscribe [:get-completed-status :1])}
      [sa/Icon {:name "flag checkered"}]
      [sa/StepContent
       [sa/StepTitle "Level 1"]]]
     [sa/Step {:active @(rf/subscribe [:get-active-status :2])
               :completed @(rf/subscribe [:get-completed-status :2])}
      [sa/Icon {:name "flag checkered"}]
      [sa/StepContent
       [sa/StepTitle "Level 2"]]]
     [sa/Step {:active @(rf/subscribe [:get-active-status :3])
               :completed @(rf/subscribe [:get-completed-status :3])}
      [sa/Icon {:name "flag checkered"}]
      [sa/StepContent
       [sa/StepTitle "Level 3"]]]
     [sa/Step {:active @(rf/subscribe [:get-active-status :4])
               :completed @(rf/subscribe [:get-completed-status :4])}
      [sa/Icon {:name "flag checkered"}]
      [sa/StepContent
       [sa/StepTitle "Level 4"]]]]]
   [:div.row
    [sa/Embed {:id
               @(rf/subscribe [:get-video-id (keyword (str @(rf/subscribe [:get-level])))])
               :source "youtube"
               :className "video"}]]
   [:div.flexButton
    [sa/Button {:content "Prev"
                :icon "left arrow"
                :labelPosition "left"
                :onClick #(prev-operation)}]
    [sa/Button {:content "Next"
                :icon "right arrow"
                :labelPosition "right"
                :onClick #(next-operation)}]]])

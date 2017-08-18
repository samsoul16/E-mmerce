(ns emmerce.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [emmerce.ajax :refer [load-interceptors!]]
            [emmerce.handlers]
            [emmerce.subscriptions]
            [reagent-material-ui.core :as ui]
            [emmerce.pages.login :as login]
            [emmerce.pages.home :as home]
            [emmerce.pages.game :as game]
            [emmerce.pages.chatbot :as chatbot]
            [emmerce.pages.dashboard :as dashboard]
            [emmerce.pages.videoref :as videoref])
  (:import goog.History))

;; Material UI HELPERS
(def reactify r/as-element)
(defn icon [name] [ui/FontIcon {:className "material-icons"} name])
(defn color [name] (aget ui/colors name))


;; App Theme
(defonce theme-defaults {:muiTheme (ui/getMuiTheme
                                    (-> ui/darkBaseTheme
                                        (js->clj :keywordize-keys true)
                                        (update :palette merge {:primary1Color (color "amber500")
                                                                :primary2Color (color "amber700")})
                                        clj->js))})


(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:img {:src (str js/context "/img/warning_clojure.png")}]]]])

(defn home-page []
  [:div
   #_[simple-nav]
   [:h2 "Welcome to a simple, example application."]])

(defn login-page []
  [:div])

(defn chatbot-page []
  [:div])

(defn game-page []
  [:div])

(defn dashboard-page []
  [:div])

(defn videoref-page []
  [:div])

(def pages
  {:home #'home-page
   :about #'about-page})

(defn page []
  [ui/MuiThemeProvider theme-defaults
   [:div
    [:div
     [(pages @(rf/subscribe [:page]))]]]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (rf/dispatch [:set-active-page :home]))

(secretary/defroute "/about" []
  (rf/dispatch [:set-active-page :about]))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET "/docs" {:handler #(rf/dispatch [:set-docs %])}))

(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))

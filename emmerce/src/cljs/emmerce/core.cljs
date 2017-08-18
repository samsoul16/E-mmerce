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
(defn icon [nme] [ui/FontIcon {:className "material-icons"} nme])
(defn color [name] (aget ui/colors name))


;; App Theme
(defonce theme-defaults {:muiTheme (ui/getMuiTheme
                                    (-> ui/lightBaseTheme
                                        (js->clj :keywordize-keys true)
                                        (update :palette merge {:primary1Color (color "purple500")
                                                                :primary2Color (color "purple700")
                                                                :primary3Color (color "purple100")
                                                                :accent1Color (color "pinkA200")
                                                                :textColor (color "grey900")
                                                                :alternateTextColor (color "white")
                                                                :borderColor (color "grey400")
                                                                })
                                        clj->js))})

(defn simple-nav []
  (let [is-open? (r/atom false)
        close #(reset! is-open? false)]
    (fn []
      [:div
       [ui/AppBar {:zDepth 2 :title "Emmerce in English"
                   :onLeftIconButtonTouchTap #(reset! is-open? true)}]
       [ui/Drawer
        {:docked false :width 400 :open @is-open?  :onRequestChange #(close)}
        [ui/Card
         [ui/CardMedia {:overlay (reactify [ui/CardText (str (:name @(rf/subscribe [:get-user])))])}
          [:img {:src (str js/context "/img/learn.jpg")}]]]
        [ui/Divider]
        [ui/List
         [ui/ListItem {:primaryText "Home" :leftIcon (reactify [icon "home"])
                       :onClick (fn [_] (secretary/dispatch! "/home") (close))}]
         [ui/Divider]
         [ui/ListItem {:primaryText "Chat with Evelyn" :leftIcon (reactify [icon "chat"])
                       :onClick (fn [_] (secretary/dispatch! "/chatbot") (close))}]
         [ui/Divider]
         [ui/ListItem {:primaryText "Video Course" :leftIcon (reactify [icon "ondemand_video"])
                       :onClick (fn [_] (secretary/dispatch! "/videos") (close))}]
         [ui/Divider ]
         [ui/ListItem {:primaryText "Play the Game" :leftIcon (reactify [icon "games"])
                       :onClick (fn [_] (secretary/dispatch! "/game") (close))}]
         [ui/Divider]
         [ui/ListItem {:primaryText "Your Dashboard" :leftIcon (reactify [icon "dashboard"])
                       :onClick (fn [_] (secretary/dispatch! "/dashboard") (close))}]
         [ui/Divider]
         [ui/ListItem {:primaryText "Logout" :leftIcon (reactify [icon "exit_to_app"])
                       :onClick (fn [_]
                                  (secretary/dispatch! "/")
                                  (close)
                                  (.signOut (.getAuthInstance js/gapi.auth2)))}]]]])))

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:img {:src (str js/context "/img/warning_clojure.png")}]]]])

(defn home-page []
  [:div
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
  {:login #'login/login
   :home #'home/home
   :chatbot #'chatbot/chatbot
   :videos #'videoref/videoref
   :game #'game/game
   :dashboard #'dashboard/dashboard})

(defn page []
  [ui/MuiThemeProvider theme-defaults
   [:div
    (let [active-page @(rf/subscribe [:page])]
      [:div
       (when (not= active-page :login)
         [simple-nav])
       [(pages active-page)]])]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (rf/dispatch [:set-active-page :login]))

(secretary/defroute "/login" []
  (rf/dispatch [:set-active-page :login]))

(secretary/defroute "/home" []
  (rf/dispatch [:set-active-page :home]))

(secretary/defroute "/chatbot" []
  (rf/dispatch [:set-active-page :chatbot]))

(secretary/defroute "/videos" []
  (rf/dispatch [:set-active-page :videos]))

(secretary/defroute "/game" []
  (rf/dispatch [:set-active-page :game]))

(secretary/defroute "/dashboard" []
  (rf/dispatch [:set-active-page :dashboard]))


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

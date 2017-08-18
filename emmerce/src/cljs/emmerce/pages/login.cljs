(ns emmerce.pages.login
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [emmerce.ajax :refer [load-interceptors!]]
            [emmerce.handlers]
            [emmerce.subscriptions]
            [reagent-material-ui.core :as ui]
            [cljs.core.async :refer [put! chan <! >! buffer]]))

;; Material UI HELPERS
(def reactify r/as-element)
(defn icon [nme] [ui/FontIcon {:className "material-icons"} nme])
(defn color [name] (aget ui/colors name))


;;;;;;;;;;;;;;;;NEW HOME PAGE
(enable-console-print!)
(def user (r/atom {}))

(defn load-gapi-auth2 []
  (let [c (chan)]
    (.load js/gapi "auth2" #(go (>! c true)))
    c))

(defn auth-instance []
  (.getAuthInstance js/gapi.auth2))

(defn get-google-token []
  (-> (auth-instance) .-currentUser .get .getAuthResponse .-id_token))

(defn handle-user-change
  [token]
  (println (str token))
  #_(GET (str server "passtoken")
       {:params {:token (get-google-token)}
        :format :json
        :response-format :json
        :keywords? true
        :handler handled-call
        :error-handler error-handler})
  (let [profile (.getBasicProfile token)]
    (reset! user
            {:name       (if profile (.getName profile) nil)
             :image-url  (if profile (.getImageUrl profile) nil)
             :token      (get-google-token)
             :signed-in? (.isSignedIn token)})
    #_(rf/dispatch [:set-active-user (.getName profile) (.getEmail profile)])
    (println "SET USER DETAILS >>" (.getName profile) " Email " (.getEmail profile)))
  #_(when-not (nil? token)
    ))



(defn handle-login [token]
  (println "LOGGED IN & current user is ")
  (let [profile (.getBasicProfile token)]
    (rf/dispatch [:set-active-user (.getName profile) (.getEmail profile)])
    (println "SET USER DETAILS >>" (.getName profile) " Email " (.getEmail profile))
    (rf/dispatch [:set-active-page :home])))

(defonce _ (go
             (<! (load-gapi-auth2))
             (.init js/gapi.auth2
                    (clj->js {"client_id"
                      "820624577284-pnnnj9h7reov4pkk0i3n3fkiiq6s28o4.apps.googleusercontent.com" "scope" "profile"}))
             (let [current-user (.-currentUser (auth-instance))]
               (println current-user)
               (.listen current-user handle-login))))

(defn home-page []
  [:div
   (if-not (:signed-in? @user) [:a {:href "#"  :on-click #(.signIn (auth-instance))} "Sign in with Google"]
           [:div
            [:p
             [:strong (:name @user)]
             [:br]
             [:img {:src (:image-url @user)}]]
            [:a {:href "#" :on-click #(.signOut (auth-instance))} "Sign Out"]])])


(defn login []
  [:div.login.dark-primary-color
   [ui/Paper {:zDepth 3 :className "login-box"}
    [:img {:src (str js/context "/img/logo.png") :height "250px" :width "200px"}]
    [:h1  "E-mmerse"]
    [ui/RaisedButton {:className "login-btn"  :labelPosition "before"
                      :backgroundColor "#29B6F6" :fullWidth true :label "Login using Google"
                      :labelStyle {:color "#FFFFFF" :font-size "20px"}
                      :onClick #(.signIn (auth-instance))}]]
   [home-page]])

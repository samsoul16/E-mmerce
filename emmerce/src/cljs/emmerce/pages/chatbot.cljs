(ns emmerce.pages.chatbot
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [emmerce.ajax :refer [load-interceptors!]]
            [emmerce.handlers]
            [emmerce.subscriptions]
            [reagent-material-ui.core :as ui]))

(def languages ["AFRIKAANS" "ALBANIAN" "AMHARIC" "ARABIC"  "ARMENIAN" "AZERBAIJANI" "BASQUE" "BELARUSIAN" "BENGALI" "BIHARI" "BULGARIAN" "BURMESE" "CATALAN" "CHEROKEE" "CHINESE" "CHINESE_SIMPLIFIED" "CHINESE_TRADITIONAL" "CROATIAN"  "CZECH" "DANISH" "DHIVEHI" "DUTCH" "ENGLISH" "ESPERANTO" "ESTONIAN" "FILIPINO" "FINNISH" "FRENCH" "GALICIAN" "GEORGIAN" "GERMAN" "GREEK" "GUARANI" "GUJARATI" "HEBREW" "HINDI" "HUNGARIAN" "ICELANDIC" "INDONESIAN" "INUKTITUT" "IRISH" "ITALIAN" "JAPANESE" "KANNADA" "KAZAKH" "KHMER" "KOREAN" "KURDISH" "KYRGYZ" "LAOTHIAN" "LATVIAN"  "LITHUANIAN" "MACEDONIAN" "MALAY" "MALAYALAM"  "MALTESE" "MARATHI" "MONGOLIAN" "NEPALI" "NORWEGIAN" "ORIYA" "PASHTO" "PERSIAN" "POLISH" "PORTUGUESE" "PUNJABI" "ROMANIAN" "RUSSIAN" "SANSKRIT" "SERBIAN" "SINDHI" "SINHALESE" "SLOVAK" "SLOVENIAN" "SPANISH" "SWAHILI" "SWEDISH" "TAJIK" "TAMIL" "TAGALOG" "TELUGU" "THAI" "TIBETAN" "TURKISH" "UKRAINIAN" "URDU" "UZBEK" "UIGHUR" "VIETNAMESE" "WELSH" "YIDDISH"])

;; Server url
(def server "http://localhost:3000/")

;; Log Function
(defn log [& msgs]
  (.log js/console (apply str msgs)))

;; ON CLICK HANDLERS
(defn recieve-chat [response]
  (log "CHAT>>>" (:content response))
  (rf/dispatch [:set-chats (:content response)]))

(defn error-handler [args]
  (log "ERRORS>>>" args))

;; Material UI HELPERS
(def reactify r/as-element)
(defn icon [nme] [ui/FontIcon {:className "material-icons"} nme])
(defn color [name] (aget ui/colors name))

(defn chatbot []
  (let [curr-lang (r/atom "ENGLISH")
        change-lang (fn [e idx val] (reset! curr-lang val))
        chats (rf/subscribe [:get-chats])]
    (fn []
      (println (str chats))
      [:div.chat-bot
       [ui/Paper {:zDepth 3 :className "chat-paper"}
        [:div {:style {:display "flex" :justify-content "space-between"}}
         [ui/CardTitle "Select Your Language"]
         [ui/DropDownMenu {:value @curr-lang :autoWidth false :style {:width 250}
                           :onChange change-lang}
          (doall (map-indexed (fn [idx lang] ^{:key idx} [ui/MenuItem {:value lang :primaryText lang}]) languages))]]
        [ui/Card {:className "chat-box"}
         (doall (map-indexed (fn [idx chat] ^{:key idx}
                               [:div
                                [:div {:className "chat-user" } [:p (:original chat)] [:img {:src (str js/context "/img/boy.png")}]]
                                [:div {:className "chat-evelyn"} [:img {:src (str js/context "/img/girl.png")}] [:p (:translated chat)]]])
                             @chats))]
        [:div {:style {:display "flex" :justify-content "space-around"}}
         [ui/TextField {:id "ipText" :floatingLabelText "What you wanna know in English?" :fullWidth true :style {:margin "0px" :padding "10px"}}]
         [ui/IconButton {:style {:height 90 :width 90 :padding 20} :tooltip "Hit Send"
                         :onClick (fn [e]
                                    (POST (str server "addchat")
                                         {:params {:email @(rf/subscribe [:get-user-email])
                                                   :lang @curr-lang
                                                   :text (.-value (.getElementById js/document "ipText"))}
                                          :format :json
                                          :response-format :json
                                          :keywords? true
                                          :handler recieve-chat
                                          :error-handler error-handler}))}
          (reactify [:img {:src (str js/context "/img/send-button.png")}])]]]])))

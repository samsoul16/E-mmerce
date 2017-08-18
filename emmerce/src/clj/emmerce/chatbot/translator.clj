(ns emmerce.chatbot.translator
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [emmerce.chatbot.languages :as lang]))


;;;;;BASE API PARAMETERS
(def base-url "https://translate.yandex.net/api/v1.5/tr.json/translate")
(def api-key "trnsl.1.1.20170818T072014Z.84146692f91b5963.921bac8a452888c0b4ef49c4617326f95a277ed9")
(def base-params {:form-params
                  {:key api-key
                   :text ""
                   :lang ""
                   :format "plain"}})

;;;;;TRANSLATION

(defn trans-param
  "Concats the from and to part of the request."
  ([from]
   (trans-param from "ENGLISH"))
  ([from to]
   (str (lang/get-language-code from) "-" (lang/get-language-code to))))

(defn translate [from text]
  (let [request (http/post "https://translate.yandex.net/api/v1.5/tr.json/translate?"
                           {:form-params
                            {:key api-key
                             :text text
                             :lang (trans-param from)
                             :format "plain"}})
        body (:body request)]
    (first (:text (json/read-str body
                                 :key-fn keyword)))))

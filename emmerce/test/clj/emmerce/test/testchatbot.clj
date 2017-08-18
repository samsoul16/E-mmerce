(ns emmerce.test.testchatbot
  (:require [emmerce.chatbot.translator :refer :all]
            [expectations :as expect]))

;;;Proper Input-Proper output
(expect/expect "Hello" (translate "SPANISH" "Hola"))

;;;INPUT LANGUAGE SAME AS OUTPUT LANGUAGE
(expect/expect "Hola" (translate "ENGLISH" "Hola"))

;;;;Sentence as Input
(expect/expect "Hello World" (translate "SPANISH" "Hola Mundo"))

;;;;;Numeber in Input with
(expect/expect "Hello 1" (translate "SPANISH" "Hola Mundo1"))

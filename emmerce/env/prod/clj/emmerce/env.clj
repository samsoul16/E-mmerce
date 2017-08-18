(ns emmerce.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[emmerce started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[emmerce has shut down successfully]=-"))
   :middleware identity})

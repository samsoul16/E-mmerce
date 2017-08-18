(ns emmerce.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [emmerce.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[emmerce started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[emmerce has shut down successfully]=-"))
   :middleware wrap-dev})

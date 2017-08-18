(ns user
  (:require [mount.core :as mount]
            [emmerce.figwheel :refer [start-fw stop-fw cljs]]
            emmerce.core))

(defn start []
  (mount/start-without #'emmerce.core/repl-server))

(defn stop []
  (mount/stop-except #'emmerce.core/repl-server))

(defn restart []
  (stop)
  (start))



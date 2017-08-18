(ns ^:figwheel-no-load emmerce.app
  (:require [emmerce.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)

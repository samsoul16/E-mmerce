(ns emmerce.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [emmerce.core-test]))

(doo-tests 'emmerce.core-test)


(ns emmerce.views.videoref
  (:require [emmerce.db.core :as db]))

(defn get-result
  "Retrieves all the level"
  [email]
  {:status true
   :content (map #(select-keys % [:level]) (db/get-level email))})

(defn get-error-result
  []
  {:status false
   :content nil})

(defn update-level
  "Updates the current level"
  [email level]
  (if (db/update-level email level)
    (get-result email)
    (get-error-result)))

(defn set-initial-level
  "Sets the initial level to 1"
  [email]
  (if (db/set-initial-level email)
    (get-result email)
    (get-error-result)))

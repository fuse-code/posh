(ns posh.clj.datascript
  (:require [posh.plugin-base :as base]
            [posh.lib.ratom :as rx]
            [datascript.core :as d]))

(defn derive-reaction [reactions key f & local-mixin]
  (apply rx/make-reaction
    #(apply f (mapv deref reactions))
    local-mixin))

(def dcfg
  (let [dcfg {:db            d/db
              :pull*         d/pull
              :pull-many     d/pull-many
              :q             d/q
              :filter        d/filter
              :with          d/with
              :entid         d/entid
              :transact!     d/transact!
              :listen!       d/listen!
              :conn?         d/conn?
              :ratom         rx/atom
              :make-reaction rx/make-reaction
              :derive-reaction derive-reaction}]
    (assoc dcfg :pull (partial base/safe-pull dcfg))))

(base/add-plugin dcfg)

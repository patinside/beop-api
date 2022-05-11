(ns api-beop.components.kv-store
  (:require [com.stuartsierra.component :as component]
            [datalevin.core :as kv]))

(defrecord KVDB [app-components]
  component/Lifecycle

  (start [this]
    (let [db-path (-> app-components :db :path)
          kvdb (kv/open-kv db-path)]
      (kv/open-dbi kvdb "vote")
      (assoc this :instance kvdb)))

  (stop [this]
    (kv/close-kv (:instance this))
    (assoc this :instance nil)))

(defn make-kv
  [app-components]
  (map->KVDB {:app-components app-components}))

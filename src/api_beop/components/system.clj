(ns api-beop.components.system
  (:require [api-beop.components.webserver :as webserver]
            [api-beop.components.kv-store :as kv]
            [com.stuartsierra.component :as component]))

(defn make-system [config]
  (component/system-map
    :db (kv/make-kv config)
    :server (component/using
              (webserver/make-server config)
              {:db :db})))

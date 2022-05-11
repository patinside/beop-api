(ns api-beop.components.webserver
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [api-beop.core :refer [app]]))

(defrecord WebServer [http-server config db]
  component/Lifecycle
  (start [this]
    (let [{:keys [webserver] :as _server-conf} config
          server (jetty/run-jetty
                   (app {:components config :db db}) webserver)]
      (if http-server
        this
        (assoc this :http-server
                   server))))
  (stop [this]
    (when http-server
      (.stop http-server))
    (assoc this :http-server nil)))

(defn make-server
  "Returns a new instance of the web server component which
  creates its handler dynamically."
  [config]
  (map->WebServer  {:config config}))

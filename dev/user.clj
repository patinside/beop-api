(ns user
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.namespace.repl :refer (refresh)]
            [api-beop.components.system :as system]
            [clojure.edn :as edn]))


(def system nil)

(def options (edn/read-string (slurp "conf/dev.edn")))

(defn init []
  (alter-var-root #'system
                  (constantly (system/make-system options))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh :after 'user/go))

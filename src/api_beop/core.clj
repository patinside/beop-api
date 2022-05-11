(ns api-beop.core
  (:require [api-beop.components.system :as system]
            [clojure.edn :as edn]
            [com.stuartsierra.component :as component])
  (:gen-class))

(defn -main
  []
  (let [conf (edn/read-string (slurp "conf/prod.edn"))
        system (system/make-system conf)]
    (component/start system)))

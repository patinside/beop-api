(ns api-beop.services.kv-store
  (:require [datalevin.core :as d]))

(def kv-db (d/open-kv "db/mykvdb"))

(defn- open-table
  [db table]
  (d/open-dbi db table))

(defn make-id
  [advert-id vote-id]
  (keyword(str (name advert-id) "." (name vote-id))))

(defn inc-vote!
  [db table advert-id vote-id]
  (let [id (make-id advert-id vote-id)]
    (if-let [prev-value (d/get-value db table id)]
      (d/transact-kv db [[:put table id (inc prev-value)]])
      (d/transact-kv db [[:put table id 1]]))))

(defn get-vote
  [db table advert-id vote-id]
  (let [id (make-id advert-id vote-id)
        _ (prn id)]
    (or (d/get-value db table id) 0)))

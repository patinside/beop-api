(ns api-beop.domain
  (:require [api-beop.services.kv-store :as kv]))

(def data
  {:1 {:accessibility {:label "Accessibility"}
       :inclusivity {:label "Inclusivity"}
       :adjustability {:label "Adjustability"}}})

(defn- get-question-data
  [{:keys [advert-id]}]
  (get data (keyword advert-id)))

(defn make-question-data-resp
  [{:keys [params] :as _req}]
  {:body (get-question-data params)})

(defn manage-widget-click
  [{:keys [components params]}]
  (let [db (-> components :db :instance)
        {:keys [advert-id vote-id]} params
        inc-vote (kv/inc-vote! db "vote" advert-id vote-id)
        read-votes (kv/get-vote db "vote" advert-id vote-id)]
    {:new-count read-votes
     :transaction-status inc-vote
     :params params}))

(defn make-advert-status
  [{:keys [components params]}]
  (let [{:keys [advert-id]} params
        db (-> components :db :instance)
        vote-ids (-> data
                     (get (keyword advert-id))
                     keys)
        results (into {} (map
                           (fn [vote-id]
                             {(keyword vote-id) (kv/get-vote db "vote" advert-id (name vote-id))})
                           vote-ids))]
    {:result results
     :params params}))

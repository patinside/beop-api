(ns api-beop.core
  (:require [compojure.core :as compojure]
            [compojure.handler :as handler]
            [api-beop.services.kv-store :as kv]
            [ring.middleware.json :refer [wrap-json-params]]
            [cheshire.core :as json]))

(def data
  {:1 {:accessibility {:label "Accessibility"}
       :inclusivity {:label "Inclusivity"}
       :adjustability {:label "Adjustability"}}})

(defn get-question-data
  [{:keys [advert-id]}]
  (get data (keyword advert-id)))

(defn make-question-data-resp
  [{:keys [params] :as _req}]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (get-question-data params)})

(defn manage-widget-clic
  [{:keys [components params]}]
  (let [db (-> components :db :instance)
        {:keys [advert-id vote-id]} params
        inc-vote (kv/inc-vote! db "vote" advert-id vote-id)
        read-votes (kv/get-vote db "vote" advert-id vote-id)]
    {:status 200
     :new-count read-votes
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
    {:status 200
     :result results
     :params params}))

(compojure/defroutes routes
                     (compojure/GET "/question-data" request (json/encode (make-question-data-resp request)))
                     (compojure/GET "/count-clic" request (json/encode (manage-widget-clic request)))
                     (compojure/GET "/advert-campaign-status" request (json/encode (make-advert-status request))))

(defn- wrap-with-dep [f deps]
  (fn [req]
    (f (merge req [:components deps]))))

(defn app
  "Returns the web handler function as a closure over the
  application component."
  [components]
  (-> routes
      (wrap-json-params)
      (wrap-with-dep components)
      (handler/api)))

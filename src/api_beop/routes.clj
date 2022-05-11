(ns api-beop.routes
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.handler :as handler]
            [api-beop.domain :as domain]
            [ring.middleware.json :refer [wrap-json-params]]
            [cheshire.core :as json]))

(defroutes routes
           (GET "/question-data" request (json/encode (domain/make-question-data-resp request)))
           (POST "/count-click" request (json/encode (domain/manage-widget-click request)))
           (GET "/advert-campaign-status" request (json/encode (domain/make-advert-status request))))

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

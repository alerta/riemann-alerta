; -*- mode: clojure; -*-
; vim: filetype=clojure

(ns alerta
  "Forwards events to Alerta."
  (:require [clj-http.client :as http]
            [cheshire.core :as json])
  (:use [riemann.common :only [localhost]]))

(def version "5.0.1")

(defn- auth-request
  [req]
  (if-let [api-key (:api-key req)]
    (-> req (dissoc :api-key)
        (assoc-in [:headers "authorization"]
                  (str "Key " api-key)))
    req))

(defn- post
  "POST to the Alerta API."
  [url body options]
  (http/post
   url
   (auth-request
    (merge
     {:body                  (json/generate-string body)
      :content-type          :json
      :accept                :json
      :socket-timeout        5000
      :conn-timeout          5000
      :throw-entire-message? true}
     options))))

(defn- format-event
  "Formats an event for Alerta."
  [event]
  {:resource    (:host event)
   :event       (get event :event (:service event))
   :environment (get event :environment "Production")
   :severity    (:state event)
   :service     [(get event :grid "Platform")]
   :group       (get event :group "Performance")
   :value       (:metric event)
   :text        (:description event)
   :tags        (into [] (:tags event))
   :origin      (str "riemann/" (localhost))
   :eventType   "performanceAlert"
   :rawData     event})

(defn alert
  "Forwards events to Alerta.

  ```clojure
  (let [alert (alerta/alert {:endpoint \"...\"
                             :api-key \"...\"
                             :debug true})]
    (changed-state alert))
  ```"
  [opts]
  (let [opts (merge {:endpoint "http://localhost:8080"} opts)]
    (fn [event] (post (str (:endpoint opts) "/alert") (format-event event) opts))))

(defn heartbeat
  "Sends heartbeat to Alerta."
  [opts]
  (let [opts     (merge {:endpoint "http://localhost:8080"} opts)
        hb-event {:origin (str "riemann/" (localhost)) :tags [version]}]
    (fn [event] (post (str (:endpoint opts) "/heartbeat") hb-event opts))))

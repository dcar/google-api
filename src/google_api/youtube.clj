(ns google-api.youtube
  (:require [clj-http.client :as client]
             [cheshire.core :as json])
  (:use google-api.core))

(def^{:private true} playlist-url "https://www.googleapis.com/youtube/v3/playlists")
(def^{:private true} playlist-item-url "https://www.googleapis.com/youtube/v3/playlistItems")

(defn search
  "youtube query api function with options arg as a map"
  [options]
  (query-req options "https://www.googleapis.com/youtube/v3/search"))
         
(defn playlist-insert
  "insert a playlist"
  [options token json-map]
  (let [json-str (json/encode json-map)]
    (json-req :post options json-str token playlist-url)))

(defn playlist-delete
  "removes a playlist from authed account"
  [id token]
  (delete-req id playlist-url token))

(defn playlists
  "retrieve a list of playlists from a channel or set :mine to true and use token"
  ([options]
    (query-req options playlist-url))
  ([options token]
    (query-req options playlist-url token)))

(defn playlist-items
  [options]
  (query-req options playlist-item-url))

(defn playlist-item-insert
  [options token json-map]
  (let [json-str (json/encode json-map)]
    (json-req :post options json-str token playlist-item-url)))

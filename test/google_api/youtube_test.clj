(ns google-api.youtube-test
  (:use clojure.test
        google-api.youtube
        ring.adapter.jetty))

(def y-key "insert_a_youtube_api_key")

(def search-options {:part "id:snippet" :q "coldplay - yellow" :key y-key :type "video"})

(def playlist-options {:part "snippet" :key y-key})

(def playlist-snippet {:snippet {:title "ambient"}})

(def playlist-list {:part "snippet" :key y-key :channelId "insert_some_channel_ID"})

(deftest search-url-test
  (testing "the search function!"
    (is (contains? (search search-options) :error))))

(deftest playlist-create-error
  (testing "If the function playlist-insert post request is formed correctly."
    (is (contains? (playlist-insert playlist-options "access_token_here" playlist-snippet) :error))))
    
(deftest playlist-list-test
  (testing "If the playlist list function returns proper json"
    (is (contains? (playlists playlist-list) :items))))

(deftest playlist-delete-test
  (testing "If the delete functions returns the proper error."
    (is (= (-> (playlist-delete "playlist_id" "access_token") :error :code) 401))))

(deftest playlist-items-test
  (testing "If the playlist-items functions returns proper json"
    (is 
      (contains? 
        (playlist-items {:part "snippet" :id "playlist_id" :key y-key}) :items))))

(deftest playlist-item-insert-test
  (testing "if the playlist-items insert function returns proper error"
    (is (= (-> (playlist-item-insert 
                 {:part "snippet"} {:snippet {:playlistId "blah" :resourceId {:videoId "blah"}}} "access_token_here") 
               :error :code) 
           401))))


(defn start-server [] (run-jetty {:port 8080 :join false?}))

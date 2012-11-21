(ns google-api.core-test
  (:use clojure.test
        google-api.core))

(def test-url "https://accounts.google.com/o/oauth2/auth?client_id=812741506391.apps.googleusercontent.com&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email&redirect_uri=https://oauth2-login-demo.appspot.com/oauthcallback")

(def test-url2 "https://accounts.google.com/o/oauth2/auth?client_id=812741506391.apps.googleusercontent.com&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email&redirect_uri=https://oauth2-login-demo.appspot.com/oauthcallback&state=/profile&access_type=offline")

(def arg-map {:client-id "812741506391.apps.googleusercontent.com"
              :redirect-uri "https://oauth2-login-demo.appspot.com/oauthcallback"})

(def state-map { :client-id "812741506391.apps.googleusercontent.com"
                 :redirect-uri "https://oauth2-login-demo.appspot.com/oauthcallback"
                 :access-type "offline"})

(deftest r-url-test
  (testing "a string build of the google auth url"
    (let [redirect (r-url arg-map nil)
          s-redirect (r-url state-map "/profile")]
      (is (= redirect test-url))
      (is (= s-redirect test-url2)))))

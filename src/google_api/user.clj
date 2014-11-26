(ns google-api.user
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(defn email-info [token]
  (let [token (if (map? token) (:access_token token) token)]
    (if-let [req (client/get "https://www.googleapis.com/oauth2/v1/userinfo" {:oauth-token token
                                                                              :throw-exceptions false})]
      (json/decode (:body req) true)
      (throw (Exception. "Error: No response")))))

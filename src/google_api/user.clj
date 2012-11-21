(ns google-api.user
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(defn email-info [token]
  (if-let [req (client/get "https://www.googleapis.com/oauth2/v1/userinfo" {:oauth-token (:access_token token)
                                                                            :throw-exceptions false})]
    (json/decode body true)
    (throw (Exception. "Error: No response"))))

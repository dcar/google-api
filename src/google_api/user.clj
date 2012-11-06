(ns google-api.user
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(defn email-info [token]
  (if-let [req (client/get "https://www.googleapis.com/oauth2/v1/userinfo" {:oauth-token (:access_token token)})]
    (if-let [body (:body req)]
      (json/decode body true)
      (throw (Exception. (str "Error: " (:error req)))))
    (throw (Exception. "Error: No response"))))

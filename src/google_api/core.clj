(ns google-api.core
  (:require [cheshire.core :as json]
            [clj-http.client :as client]))


(def g-url "https://accounts.google.com/o/oauth2/auth?")


(defn r-url
  "string together the url parameters for the redirect"
  [{:keys [client-id response-type scope redirect-uri approval-prompt access-type]
    :or {response-type "code"   
         scope         "https://www.googleapis.com/auth/userinfo.email"}} state] 
  (str g-url "client_id=" client-id "&"
             "response_type=" response-type "&" 
             "scope=" scope "&"
             "redirect_uri=" redirect-uri
             (when state (str "&state=" state))
             (when approval-prompt (str "&approval_prompt=" approval-prompt))
             (when access-type (str "&access_type=" access-type))))

(defn get-token
  "grabs a token using a post request"
  [code {:keys [client-id client-secret redirect-uri grant-type]
         :or {grant-type "authorization_code"}}]
  (let [body (str "client_id=" client-id "&" "client_secret=" client-secret "&" 
                  "redirect_uri=" redirect-uri "&" "grant_type=" grant-type "&" "code=" code)]
    (if-let  [req (client/post "https://accounts.google.com/o/oauth2/token"
                            { :body body
                              :content-type "application/x-www-form-urlencoded"})]
      (if-let [body (:body req)]
        (json/decode body true)
        (throw (Exception. (str "Error: " (:error req)))))
      (throw (Exception. "Error: No response!")))))

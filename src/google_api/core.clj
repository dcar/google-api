(ns google-api.core
  (:require [cheshire.core :as json]
            [clj-http.client :as client]))


(def g-url "https://accounts.google.com/o/oauth2/auth?")


(defn r-url
  "Strings together the url parameters for the redirect.
   Response-type default value is code. 
   Scope's default value is https://www.googleapis.com/auth/userinfo.email.
   Optional params are state, approval-prompt, and access-type.
   Put nil in the third argument to make state optional, although this is not recommended."
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
  "Grabs a token using a post request. 
   The default for the grant-type param is authorization_code."
  [code {:keys [client-id client-secret redirect-uri grant-type]
         :or {grant-type "authorization_code"}}]
  (let [body (str "client_id=" client-id "&" "client_secret=" client-secret "&" 
                  "redirect_uri=" redirect-uri "&" "grant_type=" grant-type "&" "code=" code)]
    (if-let  [req (client/post "https://accounts.google.com/o/oauth2/token"
                            { :body body
                              :content-type "application/x-www-form-urlencoded"
                              :throw-exceptions false})]
      (json/decode (:body req) true)
      (throw (Exception. "Error: No response!")))))

(defn query-req [options url & token]
  (let [default-params { :query-params options :throw-exceptions false}]
    (if token
      (json/decode
        (:body
          (client/get url
            (merge default-params {:oauth-token (:access_token token)}))) true)
      (json/decode
        (:body
          (client/get url default-params)) true))))

(defn json-req [method options json-body token url]
  (let [req (client/request
              {:method method :url url :query-params options
               :body json-body :oauth-token (:access_token token)
               :content-type "application/json" :throw-exceptions false})]
    (json/decode (:body req) true)))    

(defn delete-req [id url token]
  (let [req (client/delete url
              {:query-params {:id id} :throw-exceptions false})]
    (json/decode (:body req) true)))

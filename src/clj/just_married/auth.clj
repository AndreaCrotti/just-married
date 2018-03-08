(ns just-married.auth
  (:require [just-married.settings :as settings]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]))

(def authdata
  "All possible authenticated users"
  {:admin settings/admin-password})

(defn authenticate
  [req {:keys [username password]}]
  (when-let [user-password (get authdata (keyword username))]
    (when (= password user-password)
      (keyword username))))

(def basic-auth-backend
  (http-basic-backend {:realm "andreaenrica.life"
                       :authfn authenticate}))

(defmacro with-basic-auth
  [request body]
  `(if (authenticated? ~request)
     ~body
     (throw-unauthorized)))

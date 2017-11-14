(ns engn-web.util
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]))


(defn sync
  ([method url params & {:keys [time-limit] :or {time-limit 1000}}]
   (let [result (chan)
         hdlr   #(>!! result %)
         params (assoc params :handler hdlr :error-handler hdlr)]
     (method url params)
     (-> [result (timeout time-limit)]
         alts!!
         first)))
  ([method url]
   (sync method url {})))

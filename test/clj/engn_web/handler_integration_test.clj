(ns engn-web.handler-integration-test
  (:require [ajax.core :refer [GET POST]]
            [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [engn-web.handler :refer :all]
            [engn-web.server :refer [-main]]
            [engn-web.util :refer [sync]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.mock.request :refer [request]]))


;; ========================================
;;
;; This is an example of a Clojure macro,
;; which is one of the really powerful
;; feaures of Clojure. 
;;
;; ========================================

(defmacro with-server [t]
  `(let [server# (-main)]
    (try
      ~t
      (finally
        (.stop server#)))))


;; ========================================
;; Step 2.
;;
;; Add an integration test to check the
;; /channel/:id endpoint
;;
;; You will need to use the command:
;;
;; lein test :integration
;;
;; to run the test. Unlike test-refresh, we
;; won't be able to rerun the test automatically
;; because it is too slow
;; ========================================

(deftest ^:integration channels-list-test
  (testing "Test that we can fetch the list of channels from the HTTP endpoint"
    (with-server
        (let [channels (sync GET "http://localhost:3000/channel")]
          (is (= 3 (count channels)))))))

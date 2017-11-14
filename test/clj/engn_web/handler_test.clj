(ns engn-web.handler-test
  (:require
   [cheshire.core :as cheshire]
   [clojure.test :refer :all]
   [engn-web.handler :refer :all]
   [ring.mock.request :as mock]))


(defn parse-body [body]
  (cheshire/parse-string body true))


;; ========================================
;; Step 1.
;;
;; Add a test for the /channel/:id endpoint
;; using mock requests to the handler
;;
;; ========================================

(deftest channels-id-test
  (testing ""
    (let [response       (app (-> (mock/request :get "/channel/food")))
          msgs-for-food  (parse-body (:body response))
          response2      (app (-> (mock/request :get "/channel/vandy")))
          msgs-for-vandy (parse-body (:body response2))]
      (is (= (:status response) 200))
      (is (= [{:msg "Eat at fido" :time 1 :user {:name "Me" :nickname "Me"}}]
             msgs-for-food))
      (is (= [{:msg "Visit the Wondr'y" :time 1 :user {:name "Your Name" :nickname "Bob"}}]
             msgs-for-vandy)))))

(deftest channels-list-test
  (testing "Test that we can fetch the list of channels from the HTTP endpoint"
    (let [response (app (-> (mock/request :get  "/channel")))
          channels (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (> (count channels) 0)))))


(deftest channels-add-test
  (testing "Test that new channels are automatically added when we post a
            message to them"
    (let [response  (app (-> (mock/request :post  "/channel/new-channel")
                             (mock/body {:msg "Hello"})))
          msgs      (parse-body (:body response))
          chnl-msgs (get msgs :new-channel)]
      (is (= "Hello" (-> chnl-msgs
                         first
                         :msg))))))

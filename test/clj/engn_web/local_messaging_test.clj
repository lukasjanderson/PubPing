(ns engn-web.local-messaging-test
  (:require [clojure.test :refer :all]
            [engn-web.local-messaging :refer :all]))

;;
;; When you find a bug, DO NOT announce it! Keep the bugs secret until the End
;; of class.
;;
;;
;; There are 3 bugs in the local-messaging functions. Write tests to find them.
;;
;; Step 1. Write a more comprehensive test for test-messages-add. When you are
;; done, call the instructor over to look at your test.
;;
;; Step 2. Write tests for messages-get. When you are done, call the instructor
;; over to look at your test.
;;
;; Step 3. Write tests for channels-add. When you are done, call the instructor
;; over to look at your test.
;;
;; Step 4. Write tests for messages-expand and channels-list. When you are
;; done, you should have found all of the bugs. Call the instructor over to
;; verify.


(deftest test-messages-add
  (testing "Correct addition of messages to a channel"
    (is (= {"a" [{:msg "b1"} {:msg "b2"}]
            "b" [{:msg "c2"}]}
           (-> {}
               (messages-add "a" {:msg "b2"})
               (messages-add "b" {:msg "c2"})
               (messages-add "a" {:msg "b1"}))))))

(deftest test-messages-get
  (testing "Testing that messages are expanded correctly"
    (is (= [{:msg "Vanderbilt University"}]
           (messages-get {"a" [{:msg "vandy"}]} "a")))))

(deftest test channels-add)
(testing "Testing that channels are added correctly"
  (is (= {"a" []}
         (channels-add {} "a"))))

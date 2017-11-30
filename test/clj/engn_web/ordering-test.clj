(ns engn-web.ordering-test
  (:require [clojure.test :refer :all]
            [engn-web.ordering :refer :all]))

(deftest test-messages-add
  (testing "Correct addition of messages to a channel"))
  ;  (is (= {"a" [{:msg "b1"} {:msg "b2"}]
  ;          "b" [{:msg "c2"}]
  ;         (-> {})))
;               (messages-add "a" {:msg "b2"})
 ;              (messages-add "b" {:msg "c2"})
  ;             (messages-add "a" {:msg "b1"}))))))

(deftest test-messages-get
  (testing "Testing that messages are expanded correctly"))
 ;   (is (= [{:msg "Vanderbilt University"}]))))
 ;          (messages-get {"a" [{:msg "vandy"}]} "a")))))

(deftest test channels-add)
(testing "Testing that channels are added correctly")
  ;(is (= {"a" []}
  ;       (channels-add {} "a"))

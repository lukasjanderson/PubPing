(ns engn-web.local-ordering
   (:require [clojure.string :as string]))

;; ==========================================================================
;; Functions to add / list messages and channels
;; ==========================================================================

(defn categories []
  {"specialties" [{:id "Chicken Tenders" :class "entree" :price ""}
                  {:id "Nashville Hot Chicken Sandwich" :class "entree" :price ""}
                  {:id "Southern Chicken Wrap" :class "entree" :price ""}
                  {:id "Jack & Cheddar Quesadilla" :price ""}
                  {:id "Jack & Cheddar Quesadilla w/ Grilled Chicken" :price ""}
                  {:id "Jack & Cheddar Quesadilla w/ Fried Chicken" :price ""}
                  {:id "Veggie Quesadilla" :class "entree" :price ""}
                  {:id "Classic French Dip" :class "entree" :price ""}
                  {:id "Veggie Wrap" :class "entree" :price ""}
                  {:id "Jumbo Wings" :class "entree" :price ""}
                  {:id "Spicy Pork Belly Sandwich" :class "entree" :price ""}
                  {:id "Pub Turkey Club" :class "entree" :price ""}]
   "salads" [:class "entree"]
   "burgers"
   "desserts"
   "sides"
   "drinks"})

(defn messages-initial-state
  []
  {"default" [{:msg "CS 4278" :time 1 :user {:name "Your Name" :nickname "You"}}
              {:msg "vandy" :time 1 :user {:name "Your Name" :nickname "You"}}]
   "food" [{:msg "Eat at fido" :time 1 :user {:name "Me" :nickname "Me"}}]
   "vandy" [{:msg "Visit the Wondr'y" :time 1 :user {:name "Your Name" :nickname "Bob"}}]})


(defn messages-expand [msg expansions]
   (let [msg-text        (:msg msg)
         expansion-list  (seq expansions)
         replacer        (fn [text [m r]] (string/replace text m r))
         new-text        (reduce replacer msg-text expansion-list)]
     (assoc msg :msg new-text)))

(defn messages-get
  [msgs channel]
  (let [raw-msgs       (get msgs channel)
        expanded-msgs  (map #(messages-expand % (expansions)) raw-msgs)]
    expanded-msgs))

(defn message-search-hint-updater
  "Transforms the text in messages to highlight search results by adding
   '[ ]' around the matched text."
  [msg search]
  (let [search-hint   (str " [ " search " ] ")
        original-text (:msg msg)
        updated-text  (string/replace original-text search search-hint)]
    (assoc msg :msg updated-text)))


(defn messages-search
  [chnl-msgs search]
  (let [search-matcher (fn [msg] (string/includes? (:msg msg) search))
        filtered-msgs  (filter search-matcher chnl-msgs)
        hinted-msgs    (map #(message-search-hint-updater % search) filtered-msgs)]
    hinted-msgs))

(defn messages-add
  [msgs channel msg]
  (let [chnl-msgs   (vec (get msgs channel))
        chnl-msgs-t (if (> (count chnl-msgs) 2) (seq (subvec chnl-msgs 1)) (seq chnl-msgs))
        updated-msgs (conj chnl-msgs-t msg)]
    (assoc msgs channel updated-msgs)))

(defn channels-list
  [msgs]
  (keys msgs))

(defn channels-add
  [msgs channel]
  (if (nil? (get msgs channel))
      (assoc msgs channel [])
      msgs))

(ns engn-web.local-ordering
   (:require [clojure.string :as string]))

;; ==========================================================================
;; Functions to add / list messages and channels
;; ==========================================================================

(defn categories []
  {"Specialties" [{:id "Chicken Tenders" :class "entree" :price ""}
                  {:id "Nashville Hot Chicken Sandwich" :class "entree" :price ""}
                  {:id "Southern Chicken Wrap" :class "entree" :price ""}
                  {:id "Jack & Cheddar Quesadilla" :price ""}
                  {:id "Jack & Cheddar Quesadilla w/ Grilled Chicken" :price ""}
                  {:id "Jack & Cheddar Quesadilla w/ Fried Chicken" :price ""}
                  {:id "Veggie Quesadilla" :class "entree" :price ""}
                  {:id "Classic French Dip" :class "entree" :price ""}
                  {:id "Veggie Wrap" :class "entree" :price ""}
                  {:id "Jumbo Wings" :class "entree" :price ""} :sauce (sauces)
                  {:id "Spicy Pork Belly Sandwich" :class "entree" :price ""}
                  {:id "Pub Turkey Club" :class "entree" :price ""}]
   "Salads"      [{:id "Caesar Salad" :class "entree" :price ""}
                  {:id "Caesar Salad w/ Tofu" :class "entree" :price ""}
                  {:id "Caesar Salad w/ Grilled Chicken" :class "entree" :price ""}
                  {:id "Cobb Salad" :class "entree" :price "" :dressing (dressings)}
                  {:id "Cobb Salad w/ Tofu" :class "entree" :price "" :dressing (dressings)}
                  {:id "Cobb Salad w/ Grilled Chicken" :class "entree" :price "" :dressing (dressings)}
                  {:id "Cobb Salad w/ Fried Chicken" :class "entree" :price "" :dressing (dressings)}]
   "Burgers"     [{:id "Pub Burger" :class "entree" :price "" :cheese (cheeses)}
                  {:id "Pub Burger w/ Bacon" :class "entree" :price "" :cheese (cheeses)}
                  {:id "Vegan Burger" :class "entree" :price ""}]
   "Desserts"    [{:id "Chocolate Chip Cookie" :class "side" :price ""}
                  {:id "Ghirardelli Brownie" :class "side" :price ""}
                  {:id "Ghirardelli Brownie w/ Vanilla Ice Cream" :class "entree" :price ""}
                  {:id "Milkshake" :class "entree" :price "" :flavor (flavors)}
                  {:id "Root Beer Float" :class "entree" :price ""}]
   "Sides"       [{:id "Pub Fries" :class "side" :price ""}
                  {:id "Onion Rings" :class "side" :price ""}
                  {:id "Tortilla Chips" :class "side" :price ""}
                  {:id "Kettle Chips" :class "side" :price ""}
                  {:id "Cajun Sweet Potato Fries" :class "side" :price ""}
                  {:id "Cut Fruit" :class "side" :price ""}
                  {:id "Side Salad" :class "side" :price "" :dressing (dressings)}
                  {:id "Chips & Salsa" :class "side" :price ""}
                  {:id "Chips & Guacamole" :class "side" :price ""}
                  {:id "Chips & Queso" :class "side" :price ""}]
   "Drinks"      [{:id "Fountain Drink" :class "side" :price ""}
                  {:id "Bottled Water" :class "side" :price ""}]})

(defn sauces []
  {:sauce "Buffalo"
   :sauce "BBQ"
   :sauce "Asian"
   :sauce "Sriracha Honey"
   :sauce "Buffalo"
   :sauce "Buff-A-Que"
   :sauce "Plain"})

(defn dressings []
  {:dressing ""})

(defn cheeses []
  {:cheese "American"
   :cheese "Swiss"
   :cheese "Cheddar"
   :cheese "Provolone"})

(defn flavors []
  {:flavor "Chocolate"
   :flavor "Strawberry"
   :flavor "Vanilla"
   :flavor "Special"})

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

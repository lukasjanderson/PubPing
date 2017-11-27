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
                  {:id "Jumbo Wings" :class "entree" :price ""} :attribute (sauces)
                  {:id "Spicy Pork Belly Sandwich" :class "entree" :price ""}
                  {:id "Pub Turkey Club" :class "entree" :price ""}]
   "Salads"      [{:id "Caesar Salad" :class "entree" :price ""}
                  {:id "Caesar Salad w/ Tofu" :class "entree" :price ""}
                  {:id "Caesar Salad w/ Grilled Chicken" :class "entree" :price ""}
                  {:id "Cobb Salad" :class "entree" :price "" :attribute (dressings)}
                  {:id "Cobb Salad w/ Tofu" :class "entree" :price "" :attribute (dressings)}
                  {:id "Cobb Salad w/ Grilled Chicken" :class "entree" :price "" :attribute (dressings)}
                  {:id "Cobb Salad w/ Fried Chicken" :class "entree" :price "" :attribute (dressings)}]
   "Burgers"     [{:id "Pub Burger" :class "entree" :price "" :attribute (cheeses)}
                  {:id "Pub Burger w/ Bacon" :class "entree" :price "" :attribute (cheeses)}
                  {:id "Vegan Burger" :class "entree" :price ""}]
   "Desserts"    [{:id "Chocolate Chip Cookie" :class "side" :price ""}
                  {:id "Ghirardelli Brownie" :class "side" :price ""}
                  {:id "Ghirardelli Brownie w/ Vanilla Ice Cream" :class "entree" :price ""}
                  {:id "Milkshake" :class "entree" :price "" :attribute (flavors)}
                  {:id "Root Beer Float" :class "entree" :price ""}]
   "Sides"       [{:id "Pub Fries" :class "side" :price ""}
                  {:id "Onion Rings" :class "side" :price ""}
                  {:id "Tortilla Chips" :class "side" :price ""}
                  {:id "Kettle Chips" :class "side" :price ""}
                  {:id "Cajun Sweet Potato Fries" :class "side" :price ""}
                  {:id "Cut Fruit" :class "side" :price ""}
                  {:id "Side Salad" :class "side" :price "" :attribute (dressings)}
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

;;add item

(defn add-item
  [id class price attribute menu]
  (let [current-menu (vec (get menu))
        current-menu-t (if (> (count current-menu) 2) (seq (subvec current-menu 1)) (seq current-menu))
        if attribute (updated-menu (conj current-menu-t id class price attribute))
        (updated-menu (conj current-menu-t id class price))]
    (assoc menu updated-menu)))

;;add categories

;;potentially add price method?

;;be able to detect attributes

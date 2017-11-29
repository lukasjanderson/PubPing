(ns engn-web.local-ordering
   (:require [clojure.string :as string]))

;; ==========================================================================
;; Functions to add / list messages and channels
;; ==========================================================================

(defn order-initial-state []
  {[{:id "Chicken Tenders" :quantity 0 :class "entree" :category "Specialties" :price 6.00}]
   [{:id "Nashville Hot Chicken Sandwich" :quantity 0 :class "entree" :category "Specialties" :price 7.00
                  {:id "Southern Chicken Wrap" :class "entree" :price 6.00}
                  {:id "Jack & Cheddar Quesadilla" :price 6.00}
                  {:id "Jack & Cheddar Quesadilla w/ Grilled Chicken" :price 7.00}
                  {:id "Jack & Cheddar Quesadilla w/ Fried Chicken" :price 7.00}
                  {:id "Veggie Quesadilla" :class "entree" :price 6.00}
                  {:id "Classic French Dip" :class "entree" :price 7.00}
                  {:id "Veggie Wrap" :class "entree" :price 6.00}
                  {:id "Jumbo Wings" :class "entree" :price 6.00} :attribute (sauces)
                  {:id "Spicy Pork Belly Sandwich" :class "entree" :price 7.00}
                  {:id "Pub Turkey Club" :class "entree" :price 6.00}}]
   "Salads"      [{:id "Caesar Salad" :class "entree" :price 6.00}
                  {:id "Caesar Salad w/ Tofu" :class "entree" :price 8.00}
                  {:id "Caesar Salad w/ Grilled Chicken" :class "entree" :price 8.00}
                  {:id "Cobb Salad" :class "entree" :price 6.00 :attribute (dressings)}
                  {:id "Cobb Salad w/ Tofu" :class "entree" :price 8.00 :attribute (dressings)}
                  {:id "Cobb Salad w/ Grilled Chicken" :class "entree" :price 8.00 :attribute (dressings)}
                  {:id "Cobb Salad w/ Fried Chicken" :class "entree" :price 8.00 :attribute (dressings)}]
   "Burgers"     [{:id "Pub Burger" :class "entree" :price 6.00 :attribute (cheeses)}
                  {:id "Pub Burger w/ Bacon" :class "entree" :price 7.00 :attribute (cheeses)}
                  {:id "Vegan Burger" :class "entree" :price 6.00}]
   "Desserts"    [{:id "Chocolate Chip Cookie" :class "side" :price 1.29}
                  {:id "Ghirardelli Brownie" :class "side" :price 1.29}
                  {:id "Ghirardelli Brownie w/ Vanilla Ice Cream" :class "entree" :price 4.50}
                  {:id "Milkshake" :class "entree" :price 4.00 :attribute (flavors)}
                  {:id "Root Beer Float" :class "entree" :price 4.00}]
   "Sides"       [{:id "Pub Fries" :class "side" :price 1.50}
                  {:id "Onion Rings" :class "side" :price 2.00}
                  {:id "Tortilla Chips" :class "side" :price 1.50}
                  {:id "Kettle Chips" :class "side" :price 1.50}
                  {:id "Cajun Sweet Potato Fries" :class "side" :price 1.50}
                  {:id "Cut Fruit" :class "side" :price 1.50}
                  {:id "Side Salad" :class "side" :price 1.50 :attribute (dressings)}
                  {:id "Chips & Salsa" :class "side" :price 2.50}
                  {:id "Chips & Guacamole" :class "side" :price 3.00}
                  {:id "Chips & Queso" :class "side" :price 3.00}]
   "Drinks"      [{:id "Fountain Drink" :class "side" :price 1.79}
                  {:id "Bottled Water" :class "side" :price 1.69}]})

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

(ns engn-web.local-ordering
   (:require [clojure.string :as string]))

;; ==========================================================================
;; Functions to add / list messages and channels
;; ==========================================================================

(defn order-initial-state []
  {[{:id "Chicken Tenders" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}]
   [{:id "Nashville Hot Chicken Sandwich" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}]
   [{:id "Southern Chicken Wrap" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}]
   [{:id "Jack & Cheddar Quesadilla" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}]
   [{:id "Jack & Cheddar Quesadilla w/ Grilled Chicken" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}]
   [{:id "Jack & Cheddar Quesadilla w/ Fried Chicken" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}]
   [{:id "Veggie Quesadilla" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}]
   [{:id "Classic French Dip" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}]
   [{:id "Veggie Wrap" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}]
   [{:id "Jumbo Wings" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint "Enter choice: Buffalo, BBQ, Asian, Sriracha Honey, Buff-A-Que, Plain"}]
   [{:id "Spicy Pork Belly Sandwich" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}]
   [{:id "Pub Turkey Club" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}]
   [{:id "Caesar Salad" :quantity 0 :class "entree" :category "Salads" :price 6 :attribute "" :hint ""}]
   [{:id "Caesar Salad w/ Tofu" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint ""}]
   [{:id "Caesar Salad w/ Grilled Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint ""}]
   [{:id "Cobb Salad" :quantity 0 :class "entree" :category "Salads" :price 6 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}]
   [{:id "Cobb Salad w/ Tofu" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}]
   [{:id "Cobb Salad w/ Grilled Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}]
   [{:id "Cobb Salad w/ Fried Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}]
   [{:id "Pub Burger" :quantity 0 :class "entree" :category "Burgers" :price 6 :attribute "" :hint "Enter choice: American, Swiss, Cheddar, Provolone"}]
   [{:id "Pub Burger w/ Bacon" :quantity 0 :class "entree" :category "Burgers" :price 7 :attribute "" :hint "Enter choice: American, Swiss, Cheddar, Provolone"}]
   [{:id "Vegan Burger" :quantity 0 :class "entree" :category "Burgers" :price 6 :attribute "" :hint ""}]
   [{:id "Chocolate Chip Cookie" :quantity 0 :class "side" :category "Desserts" :price 1.29 :attribute "" :hint ""}]
   [{:id "Ghirardelli Brownie" :quantity 0 :class "side" :category "Desserts" :price 1.29 :attribute "" :hint ""}]
   [{:id "Ghirardelli Brownie w/ Vanilla Ice Cream" :quantity 0 :class "side" :category "Desserts" :price 4.50 :attribute "" :hint ""}]
   [{:id "Milkshake" :quantity 0 :class "side" :category "Desserts" :price 4 :attribute "" :hint "Enter choice: Chocolate, Strawberry, Vanilla, Special"}]
   [{:id "Root Beer Float" :quantity 0 :class "side" :category "Desserts" :price 4 :attribute "" :hint ""}]
   [{:id "Pub Fries" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}]
   [{:id "Onion Rings" :quantity 0 :class "side" :category "Sides" :price 2 :attribute "" :hint ""}]
   [{:id "Tortilla Chips" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}]
   [{:id "Kettle Chips" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}]
   [{:id "Cajun Sweet Potato Fries" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}]
   [{:id "Cut Fruit" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}]
   [{:id "Side Salad" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}]
   [{:id "Chips & Salsa" :quantity 0 :class "side" :category "Sides" :price 2.5 :attribute "" :hint ""}]
   [{:id "Chips & Guacamole" :quantity 0 :class "side" :category "Sides" :price 3 :attribute "" :hint ""}]
   [{:id "Chips & Queso" :quantity 0 :class "side" :category "Sides" :price 3 :attribute "" :hint ""}]
   [{:id "Fountain Drink" :quantity 0 :class "side" :category "Drinks" :price 1.79 :attribute "" :hint ""}]
   [{:id "Bottled Water" :quantity 0 :class "side" :category "Drinks" :price 1.69 :attribute "" :hint ""}]})

;;update quantity that takes id quantity order
;;find item in order with id
;;update item quantity



;;be able to detect attributes
;;can't i just do this in the add-item method?

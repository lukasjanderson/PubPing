(ns engn-web.local-ordering
   (:require [clojure.string :as string]))

;; ==========================================================================
;; Functions to add / list messages and channels
;; ==========================================================================

(defn order-initial-state []
  {[{:id "Chicken Tenders" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Nashville Hot Chicken Sandwich" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Southern Chicken Wrap" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Jack & Cheddar Quesadilla" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Jack & Cheddar Quesadilla w/ Grilled Chicken" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Jack & Cheddar Quesadilla w/ Fried Chicken" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Veggie Quesadilla" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Classic French Dip" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Veggie Wrap" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Jumbo Wings" :quantity 0 :class "entree" :category "Specialties" :price "" :attribute (sauces)}]
   [{:id "Spicy Pork Belly Sandwich" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Pub Turkey Club" :quantity 0 :class "entree" :category "Specialties" :price ""}]
   [{:id "Caesar Salad" :quantity 0 :class "entree" :category "Salads" :price ""}]
   [{:id "Caesar Salad w/ Tofu" :quantity 0 :class "entree" :category "Salads" :price ""}]
   [{:id "Caesar Salad w/ Grilled Chicken" :quantity 0 :class "entree" :category "Salads" :price ""}]
   [{:id "Cobb Salad" :quantity 0 :class "entree" :category "Salads" :price "" :attribute (dressings)}]
   [{:id "Cobb Salad w/ Tofu" :quantity 0 :class "entree" :category "Salads" :price "" :attribute (dressings)}]
   [{:id "Cobb Salad w/ Grilled Chicken" :quantity 0 :class "entree" :category "Salads" :price "" :attribute (dressings)}]
   [{:id "Cobb Salad w/ Fried Chicken" :quantity 0 :class "entree" :category "Salads" :price "" :attribute (dressings)}]
   [{:id "Pub Burger" :quantity 0 :class "entree" :category "Burgers" :price "" :attribute (cheeses)}]
   [{:id "Pub Burger w/ Bacon" :quantity 0 :class "entree" :category "Burgers" :price "" :attribute (cheeses)}]
   [{:id "Vegan Burger" :quantity 0 :class "entree" :category "Burgers" :price ""}]
   [{:id "Chocolate Chip Cookie" :quantity 0 :class "side" :category "Desserts" :price ""}]
   [{:id "Ghirardelli Brownie" :quantity 0 :class "side" :category "Desserts" :price ""}]
   [{:id "Ghirardelli Brownie w/ Vanilla Ice Cream" :quantity 0 :class "side" :category "Desserts" :price ""}]
   [{:id "Milkshake" :quantity 0 :class "side" :category "Desserts" :price "" :attribute (flavors)}]
   [{:id "Root Beer Float" :quantity 0 :class "side" :category "Desserts" :price ""}]
   [{:id "Pub Fries" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Onion Rings" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Tortilla Chips" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Kettle Chips" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Cajun Sweet Potato Fries" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Cut Fruit" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Side Salad" :quantity 0 :class "side" :category "Sides" :price "" :attribute (dressings)}]
   [{:id "Chips & Salsa" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Chips & Guacamole" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Chips & Queso" :quantity 0 :class "side" :category "Sides" :price ""}]
   [{:id "Fountain Drink" :quantity 0 :class "side" :category "Drinks" :price ""}]
   [{:id "Bottled Water" :quantity 0 :class "side" :category "Drinks" :price ""}]})

(defn sauces []
  {:sauce "Buffalo"
   :sauce "BBQ"
   :sauce "Asian"
   :sauce "Sriracha Honey"
   :sauce "Buffalo"
   :sauce "Buff-A-Que"
   :sauce "Plain"})

(defn dressings []
  {:dressing "Blue cheese"
   :dressing "Ranch"})

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

(defn categories []
  {"Specialties"
   "Salads"
   "Burgers"
   "Desserts"
   "Sides"
   "Drinks"})

;;update quantity that takes id quantity order
;;find item in order with id
;;update item quantity

(defn update-quantity
  [id quantity order]
  (let [order (:order (:id id :quantity quantity))
        ;;no idea about how to do attribute
        if attribute (let [order (:order (:id id :quantity quantity :attribute attribute))])]
    (keys order)))


;;be able to detect attributes
;;can't i just do this in the add-item method?

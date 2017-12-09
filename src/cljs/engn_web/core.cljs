(ns engn-web.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [cljs-time.core :as time]
              [cljs-time.format :as time-format]
              [cljs-time.coerce :as time-coerce]
              [reagent-material-ui.core :as ui]
              [ajax.core :refer [GET POST]]))

;; ==========================================================================
;; Utility functions
;; ==========================================================================

(enable-console-print!)

(defn log
  "Log a message to the Javascript console"
  [& msg]
  (.log js/console (apply str msg)))

(defn error-handler
  "Error handler for AJAX calls to print out errors to the console"
  [{:keys [status status-text]}]
  (log (str "something bad happened: " status " " status-text)))

(def el reagent/as-element)
(defn icon-span [nme] [ui/FontIcon {:className "material-icons"} nme])
(defn icon [nme] (el [:i.material-icons nme]))
(defn color [nme] (aget ui/colors nme))

;; ==========================================================================
;; App State
;; ==========================================================================

(defonce user-state (atom ""))
(defonce payment-state (atom ""))
(defonce total-state (atom 0))
(defonce comment-state (atom ""))
(defonce order-state (atom [{:id "Chicken Tenders" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            {:id "Nashville Hot Chicken Sandwich" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            {:id "Southern Chicken Wrap" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            {:id "Jack & Cheddar Quesadilla" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            {:id "Jack & Cheddar Quesadilla w/ Grilled Chicken" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            {:id "Jack & Cheddar Quesadilla w/ Fried Chicken" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            {:id "Veggie Quesadilla" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            {:id "Classic French Dip" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            {:id "Veggie Wrap" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            {:id "Jumbo Wings" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint "Enter choice: Buffalo, BBQ, Asian, Sriracha Honey, Buff-A-Que, Plain"}
                            {:id "Spicy Pork Belly Sandwich" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            {:id "Pub Turkey Club" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            {:id "Caesar Salad" :quantity 0 :class "entree" :category "Salads" :price 6 :attribute "" :hint ""}
                            {:id "Caesar Salad w/ Tofu" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint ""}
                            {:id "Caesar Salad w/ Grilled Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint ""}
                            {:id "Cobb Salad" :quantity 0 :class "entree" :category "Salads" :price 6 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            {:id "Cobb Salad w/ Tofu" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            {:id "Cobb Salad w/ Grilled Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            {:id "Cobb Salad w/ Fried Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            {:id "Pub Burger" :quantity 0 :class "entree" :category "Burgers" :price 6 :attribute "" :hint "Enter choice: American, Swiss, Cheddar, Provolone"}
                            {:id "Pub Burger w/ Bacon" :quantity 0 :class "entree" :category "Burgers" :price 7 :attribute "" :hint "Enter choice: American, Swiss, Cheddar, Provolone"}
                            {:id "Vegan Burger" :quantity 0 :class "entree" :category "Burgers" :price 6 :attribute "" :hint ""}
                            {:id "Chocolate Chip Cookie" :quantity 0 :class "side" :category "Desserts" :price 1.29 :attribute "" :hint ""}
                            {:id "Ghirardelli Brownie" :quantity 0 :class "side" :category "Desserts" :price 1.29 :attribute "" :hint ""}
                            {:id "Ghirardelli Brownie w/ Vanilla Ice Cream" :quantity 0 :class "side" :category "Desserts" :price 4.50 :attribute "" :hint ""}
                            {:id "Milkshake" :quantity 0 :class "side" :category "Desserts" :price 4 :attribute "" :hint "Enter choice: Chocolate, Strawberry, Vanilla, Special"}
                            {:id "Root Beer Float" :quantity 0 :class "side" :category "Desserts" :price 4 :attribute "" :hint ""}
                            {:id "Pub Fries" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            {:id "Onion Rings" :quantity 0 :class "side" :category "Sides" :price 2 :attribute "" :hint ""}
                            {:id "Tortilla Chips" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            {:id "Kettle Chips" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            {:id "Cajun Sweet Potato Fries" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            {:id "Cut Fruit" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            {:id "Side Salad" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            {:id "Chips & Salsa" :quantity 0 :class "side" :category "Sides" :price 2.5 :attribute "" :hint ""}
                            {:id "Chips & Guacamole" :quantity 0 :class "side" :category "Sides" :price 3 :attribute "" :hint ""}
                            {:id "Chips & Queso" :quantity 0 :class "side" :category "Sides" :price 3 :attribute "" :hint ""}
                            {:id "Fountain Drink" :quantity 0 :class "side" :category "Drinks" :price 1.79 :attribute "" :hint ""}
                            {:id "Bottled Water" :quantity 0 :class "side" :category "Drinks" :price 1.69 :attribute "" :hint ""}]))

;; List of categories
(defn categories []
  ["Specialties"
   "Salads"
   "Burgers"
   "Desserts"
   "Sides"
   "Drinks"])

;; ==========================================================================
;; View components
;; ==========================================================================

(defn add-order []
  [:div
   (for [cat (categories)]
     (do
       [:div [:b cat]]
       (for [item @order-state]
         (if (= cat (:category item))
           (do
             [ui/Card
              [ui/CardHeader {:title (:id item)
                              :subtitle (:price item)}]
              [ui/CardText (:quantity item)]
              [ui/FlatButton {:label "+"
                               :onClick #(swap! order-state assoc-in (:quantity item (+ 1 (:quantity item))))}]

              [ui/FlatButton {:label "-"
                               :onClick #(swap! order-state assoc (:quantity item (- 1 (:quantity item))))}]


              (if (not (= (:hint item) ""))
                [ui/TextField {:floatingLabelText (:hint item)
                               :style {:width "100%"
                                       :margin-left "15px"}
                               :onChange #(swap! order-state assoc (:attribute item (-> % .-target .-value)))
                               :value (:attribute item)}])])))))])

(defn add-user []
  [:div
    [ui/TextField {:floatingLabelText "Please Enter Your Commodore Card Number"
                   :style {:width "100%"
                           :margin-left "15px"}
                   :onChange #(reset! user-state (-> % .-target .-value))
                   :value @user-state}]])

(defn add-comments []
  [:div
    [ui/TextField {:floatingLabelText "Comments"
                   :onChange #(reset! comment-state (-> % .-target .-value))
                   :value @comment-state}]])

(defn payment-method []
   [ui/Card
    {:style { :background-color "#EEEEEE"}}
    [ui/Checkbox  {:style {:margin "15px"}
                   :labelPosition "left" :label "Meal Plan"}]
    [ui/Checkbox {:style {:margin "15px"}
                  :labelPosition "left" :label "Flex Meal"}]
    [ui/Checkbox {:style {:margin "15px"}
                  :labelPosition "left" :label "Meal Money"}]
    [ui/Checkbox {:style {:margin "15px"}
                  :labelPosition "left" :label "Commodore Cash"}]])

;(defn send-order []
 ; (for item menu-items))

(defn main-page []
  [ui/MuiThemeProvider
   [:div
    [:div
     {:style {:color "#546E7A"}}
     [:b [:big "Welcome to PubPing!"]]]

    (add-user)

    (add-order)

    [:div
     {:style {:color "#546E7A"}}
     [:b [:big "Comments: "]]

     (add-comments)]

    [:div
     {:style {:color "#546E7A"}}
     [:b [:big "Your Total: " (:total order-state)]]]

    [:div
     [ui/RaisedButton {:label "Order"
                       :primary true
                       :on-click send-order}]]]])

;; -------------------------
;; Routes

(def page (atom #'main-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'main-page))


;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))

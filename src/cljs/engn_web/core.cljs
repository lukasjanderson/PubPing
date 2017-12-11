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
(defonce order-state (atom {:c1 {:id "Chicken Tenders" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            :c2 {:id "Nashville Hot Chicken Sandwich" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            :c3 {:id "Southern Chicken Wrap" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            :c4 {:id "Jack & Cheddar Quesadilla" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            :c5 {:id "Jack & Cheddar Quesadilla w/ Grilled Chicken" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            :c6 {:id "Jack & Cheddar Quesadilla w/ Fried Chicken" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            :c7 {:id "Veggie Quesadilla" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            :c8 {:id "Classic French Dip" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            :c9 {:id "Veggie Wrap" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            :c10 {:id "Jumbo Wings" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint "Enter choice: Buffalo, BBQ, Asian, Sriracha Honey, Buff-A-Que, Plain"}
                            :c11 {:id "Spicy Pork Belly Sandwich" :quantity 0 :class "entree" :category "Specialties" :price 7 :attribute "" :hint ""}
                            :c12 {:id "Pub Turkey Club" :quantity 0 :class "entree" :category "Specialties" :price 6 :attribute "" :hint ""}
                            :c13 {:id "Caesar Salad" :quantity 0 :class "entree" :category "Salads" :price 6 :attribute "" :hint ""}
                            :c14 {:id "Caesar Salad w/ Tofu" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint ""}
                            :c15 {:id "Caesar Salad w/ Grilled Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint ""}
                            :c16 {:id "Cobb Salad" :quantity 0 :class "entree" :category "Salads" :price 6 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            :c17 {:id "Cobb Salad w/ Tofu" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            :c18 {:id "Cobb Salad w/ Grilled Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            :c19 {:id "Cobb Salad w/ Fried Chicken" :quantity 0 :class "entree" :category "Salads" :price 8 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            :c20 {:id "Pub Burger" :quantity 0 :class "entree" :category "Burgers" :price 6 :attribute "" :hint "Enter choice: American, Swiss, Cheddar, Provolone"}
                            :c21 {:id "Pub Burger w/ Bacon" :quantity 0 :class "entree" :category "Burgers" :price 7 :attribute "" :hint "Enter choice: American, Swiss, Cheddar, Provolone"}
                            :c22 {:id "Vegan Burger" :quantity 0 :class "entree" :category "Burgers" :price 6 :attribute "" :hint ""}
                            :c23 {:id "Chocolate Chip Cookie" :quantity 0 :class "side" :category "Desserts" :price 1.29 :attribute "" :hint ""}
                            :c24 {:id "Ghirardelli Brownie" :quantity 0 :class "side" :category "Desserts" :price 1.29 :attribute "" :hint ""}
                            :c25 {:id "Ghirardelli Brownie w/ Vanilla Ice Cream" :quantity 0 :class "side" :category "Desserts" :price 4.50 :attribute "" :hint ""}
                            :c26 {:id "Milkshake" :quantity 0 :class "side" :category "Desserts" :price 4 :attribute "" :hint "Enter choice: Chocolate, Strawberry, Vanilla, Special"}
                            :c27 {:id "Root Beer Float" :quantity 0 :class "side" :category "Desserts" :price 4 :attribute "" :hint ""}
                            :c28 {:id "Pub Fries" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            :c29 {:id "Onion Rings" :quantity 0 :class "side" :category "Sides" :price 2 :attribute "" :hint ""}
                            :c30 {:id "Tortilla Chips" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            :c31 {:id "Kettle Chips" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            :c32 {:id "Cajun Sweet Potato Fries" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            :c33 {:id "Cut Fruit" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint ""}
                            :c34 {:id "Side Salad" :quantity 0 :class "side" :category "Sides" :price 1.5 :attribute "" :hint "Enter choice: Bleu Cheese, Ranch"}
                            :c35 {:id "Chips & Salsa" :quantity 0 :class "side" :category "Sides" :price 2.5 :attribute "" :hint ""}
                            :c36 {:id "Chips & Guacamole" :quantity 0 :class "side" :category "Sides" :price 3 :attribute "" :hint ""}
                            :c37 {:id "Chips & Queso" :quantity 0 :class "side" :category "Sides" :price 3 :attribute "" :hint ""}
                            :c38 {:id "Fountain Drink" :quantity 0 :class "side" :category "Drinks" :price 1.79 :attribute "" :hint ""}
                            :c39 {:id "Bottled Water" :quantity 1 :class "side" :category "Drinks" :price 1.69 :attribute "" :hint ""}}))

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
(defn alert [text] (js/alert text))

(defn add-order []
  [:div
   (for [cat (categories)]
     (do
      [ui/Card
        [ui/CardHeader {:style {:font-weight "bold"}
                        :title cat}]
        (for [item @order-state]
          (if (= cat (get (val item) :category))
            (do
              [ui/Card
               [ui/CardHeader {:title (get (get @order-state (key item)) :id)
                               :subtitle (str "$" (get (get @order-state (key item)) :price))}]

               [ui/CardText "Quantity: " (get (get @order-state (key item)) :quantity)]

               [ui/FlatButton {:label "+"
                               :onClick #(do
                                           (swap! order-state update (key item) merge {:quantity (inc (get (get @order-state (key item)) :quantity))})
                                           (println "Quantity incremented to " (get (get @order-state (key item)) :quantity)))}]
               [ui/FlatButton {:label "-"
                               :onClick #(do
                                           (swap! order-state update (key item) merge {:quantity (dec (get (get @order-state (key item)) :quantity))})
                                           (println "Quantity decremented to " (get (get @order-state (key item)) :quantity)))}]

               (if (not (= (get (val item) :hint) ""))
                 [ui/TextField {:floatingLabelText (get (val item) :hint)
                                :style {:width "90%"
                                        :margin-left "15px"}
                                :onChange #(do
                                             (swap! order-state update (key item) merge {:attribute (-> % .-target .-value)})
                                             (println "Attribute changed to " (get (get @order-state (key item)) :attribute)))}])])))]))])

(defn inc-quantity [item]
  (println "Quantity incremented to " (inc (get (get @order-state (key item)) :quantity)))
  (update item :quantity inc))

(defn dec-quantity [item]
  (println "Quantity decremented to " (dec (get (get @order-state (key item)) :quantity)))
  (update item :quantity dec))

(defn add-user []
  [:div
    [ui/TextField {:floatingLabelText "Please Enter Your Commodore Card Number"
                   :style {:width "90%"}
                   :onChange #(reset! user-state (-> % .-target .-value))}]])

(defn add-comments []
  [:div
    [ui/TextField {:floatingLabelText "Comments"
                   :style {:width "90%"}
                   :onChange #(reset! comment-state (-> % .-target .-value))}]])

(defn payment-method []
   [ui/RadioButtonGroup {:style { :background-color "#EEEEEE"}
                         :defaultSelected "light"
                         :name "Payment Method"}
    [ui/RadioButton  {:style {:margin "15px"}
                      :label "Meal Plan"
                      :value "one"}]
    [ui/RadioButton {:style {:margin "15px"}
                     :label "Flex Meal"
                     :value "two"}]
    [ui/RadioButton {:style {:margin "15px"}
                     :label "Meal Money"
                     :value "three"}]
    [ui/RadioButton {:style {:margin "15px"}
                     :label "Commodore Cash"
                     :value "four"}]])

;(defn send-order []
 ; (for item menu-items))

(defn main-page []
  [ui/MuiThemeProvider
   [:div
    [:div
     {:style {:color "#546E7A" :margin "15px"}}
     [:b [:big "Welcome to PubPing!"]]

     (add-user)]

    (add-order)

    [:div
     {:style {:color "#546E7A" :margin "15px"}}
     [:b [:big "Comments: "]]

     (add-comments)]
    [:div
     {:style {:color "#546E7A" :margin "15px"}}
     [:b [:big "Payment Method: "]]
     (payment-method)]

    [:div
     {:style {:color "#546E7A" :margin "15px"}}
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

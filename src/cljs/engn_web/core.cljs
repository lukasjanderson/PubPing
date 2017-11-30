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

(defonce order-state (atom {:user {} :order {} :total 0 :comment ""}))

;; ==========================================================================
;; View components
;; ==========================================================================

(defn menu-design [menu categories]
  [:div
   ;;not sure how to get the specific tags for each item
   [ui/List
    (let [cat ""]
         (for [item menu]
           (do
             (if (not (cat item.category))
               {(assoc cat item.category)
                [ui/Text {:primary cat}]})
             [ui/ListItem
              {:primaryText item.id
               :secondaryText item.price
               :leftAvatar [ui/TextField :defaultValue item.quantity
                                         :onChange #(swap! item assoc :quantity %2)]}]
             (if (not item.hint "")
               [ui/TextField :floatingLabelText item.hint
                             :onChange #(swap! item assoc :attribute %2)]))))]])

(defn add-name []
  [ui/CardText
   [ui/TextField {:floatingLabelText "Please Enter Your Commodore ID Number"
                  :onChange #(swap! order-state assoc :user %2)}]
   [ui/RaisedButton {:label "Enter"
                     :on-click #(swap! order-state user)}]])

(defn add-comments []
  [ui/CardText
   [ui/TextField {:floatingLabelText "Comments"
                  :onChange #(swap! order-state assoc :comment %2)}]
   [ui/RaisedButton {:label "Enter"
                     :on-click #(swap! order-state comment)}]])

;(defn send-order []
 ; (for item menu-items))

(defn main-page []
  (add-name)

  (let [menu-items ""
        current-user(:user @order-state)
        current-price (:total @order-state)]
   [ui/MuiThemeProvider ;theme-defaults
    [:div

     (menu-design (ordering/order-initial-state) (ordering/categories))

     [:b [:big "Please Add Comments:"]]
     (add-comments)
     [:b]
     [:b [:big "Your Order Comes To: " [current-price]]]

     ;;not sure what to do with this
     [ui/RaisedButton {:label "Order" :primary true :on-click (send-order)}]]]))

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

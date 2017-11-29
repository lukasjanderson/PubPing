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


(defonce order-state (atom {:quantity 0 :user "" :total 0 :comment ""}))

(defonce quantity (atom {:quantity 0 :id ""}))

;; ==========================================================================
;; Functions to send / receive messages and list channels
;; ==========================================================================


; dropdown for just attributes; list menu items
; quantity dropdown if attributes
;

;order atom - w/ initial quantity to 0


;(defn add-msg! []
 ; (messages-add! @current-channel @msg-entry)
  ;(reset! msg-entry ""))

(defn add-item!) []
  (items-add! @order-state)
  (reset!)

;(defn add-channel! [channel]
 ; (if (not-any? #(= channel %) @channels)
  ;  (do
   ;   (POST (str "/channel/" channel)
    ;      {:params {:msg "Channel start"}
     ;      :response-format :json
      ;     :format :json
       ;    :keywords? true
        ;   :error-handler error-handler
         ;  :handler (fn [r] (log "msg posted to server"))
      ;(swap! channels conj channel))


;; ==========================================================================
;; View components
;; ==========================================================================

(defn menu-design [menu categories]
  [:div
   (for [categories menu]
    (if (= categories (item.category))
        [ui/ListItem
         {:primaryText item
          :secondaryText item.price
          :leftAvatar [ui/TextField :defaultValue item.quantity
                                    :onChange (change-order id value @order-state)]}]))]
;;old code below
  [:div
   [ui/List
    (for [category categories]
      [ui/Textfield {:floatingLabelTest category}])

    [ui/ListItem { :label item}
               :on-click #(swap! quantity assoc :quantity (+ 1 :quantity))
     [ui/]]]])

(defn add-comments1 []
  [ui/CardText
   [ui/TextField {:floatingLabelText "Comments"
                  :onChange #(swap! order-state assoc :comment %2)}]
   [ui/RaisedButton {:label "Enter"
                     :on-click #(swap! order-state comment)}]])

(defn add-name []
  [ui/CardText
   [ui/TextField {:floatingLabelText "Please Enter Your Commodore ID Number"
                  :onChange #(swap! order-state assoc :user %2)}]
   [ui/RaisedButton {:label "Enter"
                     :on-click #(swap! order-state user)}]])

(defn main-page []
  (add-name)

  (let [menu-items (add-item id)
        current-user(:user @order-state)
        current-price (:total @order-state)]
   [ui/MuiThemeProvider ;theme-defaults
    [:div

     [menu-design]

     [:b [:big "Please Add Comments:"]]
     [add-comments]
     [:b]
     [:b [:big "Your Order Comes To: " [current-price]]]

     ;;not sure what to do with this
     [ui/RaisedButton {:label "Order" :primary true :on-click send-order!!}]]]))



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

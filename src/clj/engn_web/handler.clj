(ns engn-web.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [engn-web.middleware :refer [wrap-middleware]]
            [ring.middleware.json :as json]
            [config.core :refer [env]]
            [engn-web.ordering :as ordering]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.params :refer [wrap-params]]))

;; ==========================================================================
;; Utility functions for serving up a JSON REST API
;; ==========================================================================

(def json-header
  "Utility function to set the appropriate headers in an
   HTTP response to return JSON to the client"
  {"Content-Type" "application/json"})

(defn json
  "Utility function to return JSON to the client"
  [data]
  {:status 200 :headers json-header :body data})

;; ==========================================================================
;; Functions to render the HTML for the single-page application
;; ==========================================================================

(def mount-target
  "This is the page that is displayed before figwheel
   compiles your application"
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler (this page may self-destruct)"]])

(defn head [user]
  "Function to generate the <head> tag in the main HTML page that is
   returned to the browser"
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css "https://fonts.googleapis.com/icon?family=Material+Icons")
   (include-css "https://fonts.googleapis.com/css?family=Roboto:300,400,500")
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))
   (include-user user)])

(defn main-page
   "Generates the main HTML page for the single-page application"
   [user]
   (html5
     (head user)
     [:body
       mount-target
       (include-js "/js/app.js")]))

;; ==========================================================================
;; Functions to setup the list of URI routes for the application
;; and setup the appropriate middleware wrappers
;; ==========================================================================

(defroutes routes
  (GET "/" request (main-page user))
  (not-found "Not Found"))

(def app (->  (wrap-middleware #'routes)
              (json/wrap-json-response)
              (json/wrap-json-params)
              wrap-params
              wrap-cookies))

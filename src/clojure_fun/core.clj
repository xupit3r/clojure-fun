(ns clojure-fun.core
  (:require [org.httpkit.server :as server]
            [compojure.core :as comp]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults, site-defaults]]
            [clojure.pprint :as pp]
            [clojure.data.json :as json])
  (:gen-class))

; pretend db shhh
(def people-collection (atom []))

(defn db-add-person [first last]
  (swap! people-collection conj {:first first :last last}))

; prepopulate that bad boi of a collection
(db-add-person "Joe" "D'Alessandro")
(db-add-person "Joe" "Schmoe")

(defn get-people [req]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (str (json/write-str @people-collection))})

(defn add-person [req]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (let [params (:params req)]
           (str 
            (json/write-str 
             (db-add-person (:first params) (:last params)))))})

; Simple Body Page
(defn simple-body-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})

; request-example
(defn request-example [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->>
             (pp/pprint req)
             (str "Request Object: " req))})

(defn hello-name [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->
          (pp/pprint req)
          (str "Hello " (:name (:params req))))})

(comp/defroutes app-routes
  (comp/GET "/" [] simple-body-page)
  (comp/GET "/request" [] request-example)
  (comp/GET "/hello" [] hello-name)
  (comp/GET "/people" [] get-people)
  (comp/GET "/people/add" [] add-person)
  (route/not-found "Error, page not found!"))

(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))
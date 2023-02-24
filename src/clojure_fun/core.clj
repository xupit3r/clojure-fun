(ns clojure-fun.core
  (:require [org.httpkit.server :as server]
            [compojure.core :as comp]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults, site-defaults]]
            [clojure.pprint :as pp])
  (:gen-class))

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

(comp/defroutes app-routes
  (comp/GET "/" [] simple-body-page)
  (comp/GET "/request" [] request-example)
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
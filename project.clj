(defproject clojure-fun "1.0"
  :description "some fucking around with clojure, that is it"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 ; Compojure - A basic routing library
                 [compojure "1.7.0"]
                 ; Our Http library for client/server
                 [http-kit "2.6.0"]
                 ; Ring defaults - for query params etc
                 [ring/ring-defaults "0.3.4"]
                 ; Clojure data.JSON library
                 [org.clojure/data.json "0.2.6"]]
  :repl-options {:init-ns clojure-fun.core}
  :main clojure-fun.core)

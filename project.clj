(defproject fifth-postulate "0.1.0-SNAPSHOT"
  :description "Test ClojureScript parallel compilation speedup"
  :url "https://github.com/mfikes/fifth-postulate"
  :license {:name "Eclipse Public License"
            :url "https://github.com/mfikes/fifth-postulate/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.9.0-beta4"]
                 [org.clojure/clojurescript "1.9.946"]]
  :jvm-opts ["-Xmx8g"])

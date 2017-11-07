(ns build
  (:require
    [cljs.build.api :as b]
    [cljs.compiler]))

(in-ns 'cljs.compiler)

; no perf impact, just easier to read
(defn source-map-inc-col [{:keys [gen-col] :as m} n]
  (assoc m :gen-col (+ gen-col n)))

(defn source-map-inc-line [{:keys [gen-line] :as m}]
  (assoc m
         :gen-line (inc gen-line)
         :gen-col 0))

;; string? provides pretty decent boost
(defn emit1 [x]
  (cond
    (nil? x) nil
    (string? x)
    (do (when-not (nil? *source-map-data*)
          (swap! *source-map-data* source-map-inc-col (count x)))
        (print x))
    #?(:clj (map? x) :cljs (ana/cljs-map? x)) (emit x)
    #?(:clj (seq? x) :cljs (ana/cljs-seq? x)) (run! emit1 x)
    #?(:clj (fn? x) :cljs ^boolean (goog/isFunction x)) (x)
    :else (let [s (print-str x)]
            (when-not (nil? *source-map-data*)
              (swap! *source-map-data* source-map-inc-col (count s)))
            (print s))))

(defn emits [& xs]
  (run! emit1 xs))

(defn emitln [& xs]
  (run! emit1 xs)
  (newline)
  (when *source-map-data*
    (swap! *source-map-data* source-map-inc-line))
  nil)

(in-ns 'build)

(defn go []
  (b/build "src"
    {:asset-path "js/out"
     :output-to "public/js/main.js"
     :output-dir "public/js/out"
     :parallel-build true
     :compiler-stats true
     :verbose true}))

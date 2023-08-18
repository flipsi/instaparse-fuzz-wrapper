(ns instaparse-fuzz-wrapper.core
  (:require [com.code-intelligence.jazzer-clj.core :as fuzzing]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [instaparse.core :as instaparse]))

(def example-grammars
    {:minimal (io/resource "data/minimal.txt")
     :phone (io/resource "data/phone_uri.txt")
     :abnf-uri (io/resource "data/abnf_uri.txt")})

(defn build-parser [grammar] (instaparse/parser grammar))

(defn split-into-grammar-and-string
    "Split fuzzer input into grammar string and input-string to parse"
    [input]
    (let [input-split (str/split input #"\n===============\n")
          grammar (first input-split)
          string-to-parse (second input-split)]
        [grammar string-to-parse]))

(fuzzing/deftarget
    instaparse_fuzz_wrapper.targets.first_try [fuzzer-input]
    (let [fuzzer-string (.consumeRemainingAsString fuzzer-input)
          [grammar string-to-parse] (split-into-grammar-and-string fuzzer-string)]
        (println "fuzzer-string:" fuzzer-string) ;; empty string, not init corpus FIXME
        (println "grammar:" grammar)
        (println "string:" string-to-parse)
        (try
            (let [parser (build-parser grammar)]
                (parser string-to-parse))
            (catch Throwable e
                (let [exception-regex #"Some exception that is expected"]
                    (when-not (re-matches exception-regex (ex-message e)))
                    (throw e))))))

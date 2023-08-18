(ns instaparse-fuzz-wrapper.core
  (:require [com.code-intelligence.jazzer-clj.core :as fuzzing]
            [clojure.java.io :as io]
            [instaparse.core :as instaparse]))

(instaparse/defparser
    uri-parser
    (io/resource "data/abnf_uri.txt")
    :input-format :abnf
    :instaparse.abnf/case-insensitive true)

(fuzzing/deftarget instaparse_fuzz_wrapper.targets.first_try [input]
                   (let [string (.consumeRemainingAsString input)]
                     (try (uri-parser string)
                          (catch Throwable e
                            (assert nil (ex-message e))
                            (println "RuntimeException baby" (ex-message e))
                            (when-not (re-matches #"Error parsing grammar specification"
                                                  (ex-message e))
                              (throw e))))))

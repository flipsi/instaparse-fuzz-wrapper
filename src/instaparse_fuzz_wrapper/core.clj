(ns instaparse-fuzz-wrapper.core
  (:require [com.code-intelligence.jazzer-clj.core :as fuzzing]
            [instaparse.core :as insta]))

(def phone-uri-grammar-data (slurp "src/instaparse_fuzz_wrapper/phone_uri.txt"))

(def phone-uri-grammar (insta/parser phone-uri-grammar-data))

(fuzzing/deftarget instaparse_fuzz_wrapper.targets.first_try [input]
                   (let [string (.consumeRemainingAsString input)]
                     (try (phone-uri-grammar string)
                          (catch Throwable e
                            (when-not (re-matches #"Error parsing grammar specification" ;; FIXME
                                                  (ex-message e))
                              (throw e))))))

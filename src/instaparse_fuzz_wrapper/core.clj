(ns instaparse-fuzz-wrapper.core
  (:require [com.code-intelligence.jazzer-clj.core :as fuzzing]
            [instaparse.core :as insta]))

(fuzzing/deftarget instaparse_fuzz_wrapper.targets.first_try [input]
                   (let [string (.consumeRemainingAsString input)]
                     (insta/parser string)))

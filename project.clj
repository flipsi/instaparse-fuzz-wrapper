(defproject instaparse-fuzz-wrapper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [instaparse/instaparse "1.4.12"]
                 [com.code-intelligence/jazzer "0.19.0"]
                 [com.code-intelligence/jazzer-api "0.19.0"]
                 [com.code-intelligence/jazzer-clj "0.1.0"]]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}
  :repl-options {:init-ns instaparse-fuzz-wrapper.core})


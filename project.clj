(defproject api-beop "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring "1.9.1"]
                 [ring/ring-jetty-adapter "1.9.1" ]
                 [compojure "1.6.1"]
                 [com.stuartsierra/component "1.0.0"]
                 [cheshire "5.10.2"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.xerial/sqlite-jdbc "3.23.1"]
                 [com.github.seancorfield/honeysql "2.2.891"]
                 [babashka/fs "0.1.6"]
                 [datalevin "0.6.9"]
                 [ring/ring-json "0.5.1"]]
  :repl-options {:init-ns api-beop.core}
  :profiles {:dev {:resource-paths ["dev"]}})

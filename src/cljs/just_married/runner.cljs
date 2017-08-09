(ns just-married.runner
  (:require '[doo.core :as doo]))

(let [doo-opts {:paths {:karma "karma"}}
      compiler-opts {:output-to "out/testable.js"
                     :optimizations :none}]

  (doo/run-script :phantom compiler-opts doo-opts))

#!/usr/bin/env bash

set -ex
# use a different port to avoid overlapping

lein uberjar
PORT=5001 java $JVM_OPTS \
    -cp target/uberjar/just-married.jar clojure.main \
     -m just-married.server

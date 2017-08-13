#!/usr/bin/env bash

set -ex

lein uberjar
java $JVM_OPTS -cp target/uberjar/just-married.jar \
     clojure.main -m just-married.server

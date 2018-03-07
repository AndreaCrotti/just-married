#!/usr/bin/env bash
set -xe

source prod.env && ./dump.sh
source local.env && ./load.sh

#!/usr/bin/env bash

source prod.env && ./dump.sh
source local.env && ./load.sh

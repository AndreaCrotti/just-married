#!/usr/bin/env bash

echo "Loading from dump.sql"

psql $DATABASE_URL < dump.sql

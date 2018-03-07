#!/usr/bin/env bash

DUMP_FILE="dump.sql"

echo "Dumping database to $DUMP_FILE"
pg_dump $DATABASE_URL > $DUMP_FILE

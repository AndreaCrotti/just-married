#!/usr/bin/env bash

psql -h localhost -p 5440 -U just_married just_married < schema.sql

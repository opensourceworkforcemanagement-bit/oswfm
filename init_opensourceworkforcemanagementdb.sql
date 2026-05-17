-- Run this against opensourceworkforcemanagement after init.sql:
--   psql -U postgres -p 5432 -d postgres -f init.sql
--   psql -U postgres -p 5432 -d opensourceworkforcemanagement -f init_opensourceworkforcemanagementdb.sql

\connect opensourceworkforcemanagement

CREATE EXTENSION IF NOT EXISTS postgis;
CREATE SCHEMA IF NOT EXISTS oswfm AUTHORIZATION oswfm;
CREATE EXTENSION IF NOT EXISTS postgis SCHEMA oswfm;
GRANT USAGE ON SCHEMA public TO oswfm;
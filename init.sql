-- Create user 'oswfm' if it does not exist
DO
$$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_roles
        WHERE rolname = 'oswfm'
    ) THEN
        CREATE USER oswfm WITH PASSWORD 'oswfm';
        RAISE NOTICE 'User "oswfm" created successfully';
    ELSE
        RAISE NOTICE 'User "oswfm" already exists';
    END IF;
END
$$;

-- Create database 'opensourceworkforcemanagement' if it does not exist
SELECT 'CREATE DATABASE opensourceworkforcemanagement OWNER oswfm'
WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'opensourceworkforcemanagement'
)\gexec

-- Grant all privileges on database to oswfm user
GRANT ALL PRIVILEGES ON DATABASE opensourceworkforcemanagement TO oswfm;
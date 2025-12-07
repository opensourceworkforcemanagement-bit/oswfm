-- Terminate all active connections to the database
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'opensourceworkforcemanagement'
  AND pid <> pg_backend_pid();

-- Drop database 'opensourceworkforcemanagement' if it exists
DROP DATABASE IF EXISTS opensourceworkforcemanagement;

-- Drop user 'oswfm' if it exists
DROP USER IF EXISTS oswfm;

-- Display completion message
DO
$$
BEGIN
    RAISE NOTICE 'Database cleanup completed successfully';
    RAISE NOTICE 'Database "opensourceworkforcemanagement" and user "oswfm" have been removed';
END
$$;

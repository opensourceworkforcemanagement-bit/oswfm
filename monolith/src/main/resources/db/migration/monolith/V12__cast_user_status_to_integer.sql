ALTER TABLE oswfm_users
    ALTER COLUMN user_status TYPE integer USING user_status::integer;

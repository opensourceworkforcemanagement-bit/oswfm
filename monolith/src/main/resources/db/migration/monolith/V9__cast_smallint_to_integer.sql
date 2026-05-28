-- Hibernate entities map status/type fields as Integer (INT4), but the original
-- migrations created them as int2 (SMALLINT). Cast all affected columns so that
-- schema validation passes.

ALTER TABLE employees
    ALTER COLUMN status TYPE integer USING status::integer;

ALTER TABLE employees_status_history
    ALTER COLUMN status TYPE integer USING status::integer;

ALTER TABLE departments
    ALTER COLUMN status TYPE integer USING status::integer;

ALTER TABLE organization
    ALTER COLUMN status TYPE integer USING status::integer;

ALTER TABLE projects
    ALTER COLUMN status TYPE integer USING status::integer;

ALTER TABLE email_addresses
    ALTER COLUMN type TYPE integer USING type::integer;

ALTER TABLE phone_numbers
    ALTER COLUMN type TYPE integer USING type::integer;

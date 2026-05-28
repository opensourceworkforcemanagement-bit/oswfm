-- Hibernate validates String fields as VARCHAR but the column was created as CHAR (bpchar).
-- ALTER TYPE to varchar(1) so schema validation passes; stored values are unchanged.
ALTER TABLE timesheet_entry_minutes
    ALTER COLUMN day_of_week TYPE VARCHAR(1);

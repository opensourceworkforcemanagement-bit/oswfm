-- Fix timesheet table schemas to match JPA entities.

-- 1. timesheet_entries_comments: rename timesheet_entries_timesheet_id -> timesheet_id
DROP INDEX IF EXISTS idx_timesheet_entries_comments_timesheet_id;
ALTER TABLE timesheet_entries_comments
    DROP CONSTRAINT IF EXISTS timesheet_entries_comments_timesheet_entries_timesheet_id_fkey;
ALTER TABLE timesheet_entries_comments
    RENAME COLUMN timesheet_entries_timesheet_id TO timesheet_id;
CREATE INDEX idx_timesheet_entries_comments_timesheet_id ON timesheet_entries_comments(timesheet_id);

-- 2. timesheet_entries_in_out: entity uses timesheet_id as sole PK (no timesheet_entries_in_out_id).
--    Drop dependent table first, then recreate both.
DROP TABLE IF EXISTS timesheet_entries_in_out_comments;
DROP TABLE IF EXISTS timesheet_entries_in_out;
CREATE TABLE timesheet_entries_in_out (
    timesheet_id SERIAL PRIMARY KEY,
    su1_in_time VARCHAR(12),
    m1_in_time VARCHAR(12),
    t1_in_time VARCHAR(12),
    w1_in_time VARCHAR(12),
    th1_in_time VARCHAR(12),
    f1_in_time VARCHAR(12),
    sa1_in_time VARCHAR(12),
    su2_in_time VARCHAR(12),
    m2_in_time VARCHAR(12),
    t2_in_time VARCHAR(12),
    w2_in_time VARCHAR(12),
    th2_in_time VARCHAR(12),
    f2_in_time VARCHAR(12),
    su1_out_time VARCHAR(12),
    m1_out_time VARCHAR(12),
    t1_out_time VARCHAR(12),
    w1_out_time VARCHAR(12),
    th1_out_time VARCHAR(12),
    f1_out_time VARCHAR(12),
    sa1_out_time VARCHAR(12),
    su2_out_time VARCHAR(12),
    m2_out_time VARCHAR(12),
    t2_out_time VARCHAR(12),
    w2_out_time VARCHAR(12),
    th2_out_time VARCHAR(12),
    f2_out_time VARCHAR(12),
    sa2_out_time VARCHAR(12)
);

-- 3. Archive tables: add PKs where missing (Hibernate needs sequences for @GeneratedValue).
ALTER TABLE timesheet_remarks_archive
    ADD PRIMARY KEY (timesheet_remarks_id);

ALTER TABLE timesheet_summary_archive
    ADD PRIMARY KEY (timesheet_summary_id);

ALTER TABLE timesheet_approvals_archive
    ADD PRIMARY KEY (timesheet_approval_id);

ALTER TABLE timesheet_notifications_archive
    ADD PRIMARY KEY (timesheet_notification_id);

-- timesheet_old_audit_log: no PK defined in migration
ALTER TABLE timesheet_old_audit_log
    ADD PRIMARY KEY (timesheet_audit_log_id);

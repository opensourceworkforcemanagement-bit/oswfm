-- Fix "old" timesheet table schemas to match JPA entities.

-- 1. timesheet_entries_old: entity uses timesheet_id as PK + employee_id + pay_period_id.
--    Migration has timesheet_entries_id (no PK) + no employee_id/pay_period_id.
DROP TABLE IF EXISTS timesheet_entries_old;
CREATE TABLE timesheet_entries_old (
    timesheet_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL,
    pay_period_id INTEGER NOT NULL,
    work_code_id INTEGER NOT NULL,
    account_code_id INTEGER NOT NULL,
    su1_hours DECIMAL(4,2),
    m1_hours DECIMAL(4,2),
    t1_hours DECIMAL(4,2),
    w1_hours DECIMAL(4,2),
    th1_hours DECIMAL(4,2),
    f1_hours DECIMAL(4,2),
    sa1_hours DECIMAL(4,2),
    su2_hours DECIMAL(4,2),
    m2_hours DECIMAL(4,2),
    t2_hours DECIMAL(4,2),
    w2_hours DECIMAL(4,2),
    th2_hours DECIMAL(4,2),
    f2_hours DECIMAL(4,2),
    sa2_hours DECIMAL(4,2)
);

-- 2. timesheet_entries_in_out_old: entity uses timesheet_id as sole PK.
--    Migration has timesheet_entries_timesheet_id + timesheet_id (no PK).
DROP TABLE IF EXISTS timesheet_entries_in_out_old;
CREATE TABLE timesheet_entries_in_out_old (
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
    sa2_in_time VARCHAR(12),
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

-- 3. timesheet_entries_comments_old: entity uses timesheet_id; migration has timesheet_entries_timesheet_id.
DROP INDEX IF EXISTS idx_timesheet_entries_comments_old_timesheet_id;
ALTER TABLE timesheet_entries_comments_old
    RENAME COLUMN timesheet_entries_timesheet_id TO timesheet_id;
ALTER TABLE timesheet_entries_comments_old
    ADD PRIMARY KEY (timesheet_entries_comments_id);
CREATE INDEX idx_timesheet_entries_comments_old_timesheet_id ON timesheet_entries_comments_old(timesheet_id);

-- 4. timesheet_remarks_old: columns match but no PK defined.
ALTER TABLE timesheet_remarks_old
    ADD PRIMARY KEY (timesheet_remarks_id);

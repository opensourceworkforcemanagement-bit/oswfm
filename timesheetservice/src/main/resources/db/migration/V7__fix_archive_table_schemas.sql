-- Drop and recreate archive tables whose schema diverged from the JPA entities.

-- timesheet_entries_archive: entity uses timesheet_id as PK + employee_id + pay_period_id
DROP TABLE IF EXISTS timesheet_entries_archive;
CREATE TABLE timesheet_entries_archive (
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
CREATE INDEX idx_timesheet_entries_archive_employee_id ON timesheet_entries_archive(employee_id);
CREATE INDEX idx_timesheet_entries_archive_pay_period_id ON timesheet_entries_archive(pay_period_id);

-- timesheet_entries_comments_archive: entity uses timesheet_id (not timesheet_entries_timesheet_id)
DROP TABLE IF EXISTS timesheet_entries_comments_archive;
CREATE TABLE timesheet_entries_comments_archive (
    timesheet_entries_comments_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL,
    entry_day INTEGER NOT NULL,
    comments TEXT
);
CREATE INDEX idx_timesheet_entries_comments_archive_timesheet_id ON timesheet_entries_comments_archive(timesheet_id);

-- timesheet_entries_in_out_archive: entity uses timesheet_id as SERIAL PK (no separate in_out_id)
DROP TABLE IF EXISTS timesheet_entries_in_out_archive;
CREATE TABLE timesheet_entries_in_out_archive (
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

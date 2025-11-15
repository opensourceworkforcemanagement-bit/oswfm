CREATE TABLE pay_periods (
    pay_period_id  SERIAL PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);
-- Index for faster queries
CREATE UNIQUE INDEX idx_unique_pay_period ON pay_periods(start_date, end_date);
CREATE INDEX idx_pay_periods_start_date ON pay_periods(start_date);
CREATE INDEX idx_pay_periods_end_date ON pay_periods(end_date);
CREATE INDEX idx_pay_periods_pay_period_id ON pay_periods(pay_period_id);

-- Timesheet related tables
CREATE TABLE timesheet (
    timesheet_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id)
);
-- Index for faster queries
CREATE INDEX idx_timesheet_employee_id ON timesheet(employee_id);
CREATE INDEX idx_timesheet_pay_period_id ON timesheet(pay_period_id);


-- Supporting tables for timesheet management
CREATE TABLE timesheet_operations_types (
    operation_type_id SERIAL PRIMARY KEY,
    operation_type_name VARCHAR(50) NOT NULL,
    operation_description TEXT
);
-- Index for faster queries
CREATE INDEX idx_timesheet_operations_types_operation_type_name ON timesheet_operations_types(operation_type_name);

CREATE TABLE work_codes (
    work_code_id SERIAL PRIMARY KEY,
    prefix VARCHAR(10),
    suffix VARCHAR(10),
    short_work_code VARCHAR(10) NOT NULL,
    long_work_code VARCHAR(50) NOT NULL,
    description TEXT,
    status int2 NULL
);
-- Index for faster queries
CREATE INDEX idx_work_codes_work_code ON work_codes(work_code_id);

CREATE TABLE account_codes (
    account_code_id SERIAL PRIMARY KEY,
    account_code VARCHAR(50) NOT NULL,
    description TEXT,
    status int2 NULL
);
-- Index for faster queries
CREATE INDEX idx_account_codes_account_code ON account_codes(account_code_id);

CREATE TABLE task_codes (
    task_code_id SERIAL PRIMARY KEY,
    task_code VARCHAR(50) NOT NULL,
    description TEXT,
    status int2 NULL
);  
-- Index for faster queries
CREATE INDEX idx_task_codes_task_code ON task_codes(task_code_id);
CREATE INDEX idx_task_codes_status ON task_codes(status);

-- Timesheet entries and related tables
CREATE TABLE timesheet_entries (
    timesheet_entries_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
    work_code_id INTEGER NOT NULL REFERENCES work_codes(work_code_id),
    account_code_id INTEGER NOT NULL REFERENCES account_codes(account_code_id),
    su1_hours DECIMAL(4,2) NOT NULL ,
    m1_hours DECIMAL(4,2) NOT NULL ,
    t1_hours DECIMAL(4,2) NOT NULL ,
    w1_hours DECIMAL(4,2) NOT NULL ,
    th1_hours DECIMAL(4,2) NOT NULL ,
    f1_hours DECIMAL(4,2) NOT NULL ,
    sa1_hours DECIMAL(4,2) NOT NULL ,
    su2_hours DECIMAL(4,2) NOT NULL , 
    m2_hours DECIMAL(4,2) NOT NULL ,
    t2_hours DECIMAL(4,2) NOT NULL ,
    w2_hours DECIMAL(4,2) NOT NULL ,
    th2_hours DECIMAL(4,2) NOT NULL ,
    f2_hours DECIMAL(4,2) NOT NULL ,
    sa2_hours DECIMAL(4,2) NOT NULL     
);

-- Index for faster queries
CREATE INDEX idx_timesheet_entries_timesheet_id ON timesheet_entries(timesheet_id);
CREATE INDEX idx_timesheet_task_code ON timesheet_entries(work_code_id);
CREATE INDEX idx_timesheet_account_code ON timesheet_entries(account_code_id);

CREATE TABLE timesheet_entries_in_out (
    timeshee_id SERIAL PRIMARY KEY,
    su1_in_time varchar(12),
    m1_in_time varchar(12),
    t1_in_time varchar(12),
    w1_in_time varchar(12),
    th1_in_time varchar(12),
    f1_in_time varchar(12),
    sa1_in_time varchar(12),
    su2_in_time varchar(12), 
    m2_in_time varchar(12),
    t2_in_time varchar(12),
    w2_in_time varchar(12),
    th2_in_time varchar(12),
    f2_in_time varchar(12),
    su1_out_time varchar(12),
    m1_out_time varchar(12),
    t1_out_time varchar(12),
    w1_out_time varchar(12),
    th1_out_time varchar(12),
    f1_out_time varchar(12),
    sa1_out_time varchar(12),
    su2_out_time varchar(12), 
    m2_out_time varchar(12),
    t2_out_time varchar(12),
    w2_out_time varchar(12),
    th2_out_time varchar(12),
    f2_out_time varchar(12),
    sa2_out_time varchar(12)
);

-- Index for faster queries
CREATE INDEX idx_timesheet_entries_in_out_timeshee_id ON timesheet_entries_in_out(timeshee_id);


CREATE TABLE timesheet_entries_comments (
    timesheet_entries_comments_id SERIAL PRIMARY KEY,
    timeshee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    entry_day INTEGER NOT NULL,
    comments TEXT
);

-- Index for faster queries
CREATE INDEX idx_timesheet_entries_comments_timesheet_entries_comments_id ON timesheet_entries_comments(timesheet_entries_comments_id);
CREATE INDEX idx_timesheet_entries_comments_timeshee_id ON timesheet_entries_comments(timeshee_id);


CREATE TABLE timesheet_remarks (
    timesheet_remarks_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet_entries(timesheet_id),
    remarks TEXT,
    remarks_order INTEGER,
    created_at timestamp(6) NULL,
	created_by INTEGER
);

-- Index for faster queries
CREATE INDEX idx_timesheet_remarks_timesheet_id ON timesheet_remarks(timesheet_id);


CREATE TABLE timesheet_audit_log (
    timesheet_audit_log_id SERIAL PRIMARY KEY,
    timeshee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    created_at timestamp(6) NULL,
	created_by INTEGER
    operation_type_id INTEGER NULL
); 
-- Index for faster queries
CREATE INDEX idx_timesheet_audit_log_timeshee_id ON timesheet_audit_log(timeshee_id);
CREATE INDEX idx_timesheet_audit_log_action_timestamp ON timesheet_audit_log(created_at);



CREATE TABLE timesheet_entries_old (
    timeshee_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id),
    work_code_id INTEGER NOT NULL REFERENCES work_codes(work_code_id),
    account_code_id INTEGER NOT NULL REFERENCES account_codes(account_code_id),
    su1_hours DECIMAL(4,2) NOT NULL ,
    m1_hours DECIMAL(4,2) NOT NULL ,
    t1_hours DECIMAL(4,2) NOT NULL ,
    w1_hours DECIMAL(4,2) NOT NULL ,
    th1_hours DECIMAL(4,2) NOT NULL ,
    f1_hours DECIMAL(4,2) NOT NULL ,
    sa1_hours DECIMAL(4,2) NOT NULL ,
    su2_hours DECIMAL(4,2) NOT NULL , 
    m2_hours DECIMAL(4,2) NOT NULL ,
    t2_hours DECIMAL(4,2) NOT NULL ,
    w2_hours DECIMAL(4,2) NOT NULL ,
    th2_hours DECIMAL(4,2) NOT NULL ,
    f2_hours DECIMAL(4,2) NOT NULL ,
    sa2_hours DECIMAL(4,2) NOT NULL     
);

-- Index for faster queries
CREATE INDEX idx_timesheet_entries_old_epm_id_pp_id ON timesheet_entries_old(employee_id, pay_period_id);
CREATE INDEX idx_timesheet_entries_old_work_code_id ON timesheet_entries_old(work_code_id);
CREATE INDEX idx_timesheet_entries_old_account_code ON timesheet_entries_old(account_code_id);


CREATE TABLE timesheet_entries_in_out_old (
    timeshee_id SERIAL PRIMARY KEY,
    su1_in_time varchar(12),
    m1_in_time varchar(12),
    t1_in_time varchar(12),
    w1_in_time varchar(12),
    th1_in_time varchar(12),
    f1_in_time varchar(12),
    sa1_in_time varchar(12),
    su2_in_time varchar(12), 
    m2_in_time varchar(12),
    t2_in_time varchar(12),
    w2_in_time varchar(12),
    th2_in_time varchar(12),
    f2_in_time varchar(12),
    sa2_in_time varchar(12),
    su1_out_time varchar(12),
    m1_out_time varchar(12),
    t1_out_time varchar(12),
    w1_out_time varchar(12),
    th1_out_time varchar(12),
    f1_out_time varchar(12),
    sa1_out_time varchar(12),
    su2_out_time varchar(12), 
    m2_out_time varchar(12),
    t2_out_time varchar(12),
    w2_out_time varchar(12),
    th2_out_time varchar(12),
    f2_out_time varchar(12),
    sa2_out_time varchar(12)
);

-- Index for faster queries
CREATE INDEX idx_timesheet_entries_in_out_old_timeshee_id ON timesheet_entries_in_out_old(timeshee_id);


CREATE TABLE timesheet_entries_comments_old (
    timesheet_entries_comments_id SERIAL PRIMARY KEY,
    timeshee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    entry_day INTEGER NOT NULL,
    comments TEXT
);

-- Index for faster queries
CREATE INDEX idx_timesheet_entries_comments_old_timesheet_entries_comments_id ON timesheet_entries_comments(timesheet_entries_comments_id);
CREATE INDEX idx_timesheet_entries_comments_old_timeshee_id ON timesheet_entries_comments(timeshee_id);

CREATE TABLE timesheet_remarks_old (
    timesheet_remarks_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet_entries(timesheet_id),
    remarks TEXT,
    remarks_order INTEGER,
    created_at timestamp(6) NULL,
	created_by INTEGER
);

-- Index for faster queries
CREATE INDEX idx_timesheet_remarks_old_timesheet_id ON timesheet_remarks_old(timesheet_id);


CREATE TABLE timesheet_old_audit_log (
    timesheet_audit_log_id SERIAL PRIMARY KEY,
    timeshee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    created_at timestamp(6) NULL,
	created_by INTEGER
    operation_type_id INTEGER NULL
); 
-- Index for faster queries
CREATE INDEX idx_timesheet_audit_old_log_timeshee_id ON timesheet_audit_old_log(timeshee_id);
CREATE INDEX idx_timesheet_audit_old_log_action_timestamp ON timesheet_audit_old_log(created_at);

-- Archive tables for timesheet data

CREATE TABLE timesheet_entries_archive (
    timeshee_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id),
    work_code_id INTEGER NOT NULL REFERENCES work_codes(work_code_id),
    account_code_id INTEGER NOT NULL REFERENCES account_codes(account_code_id),
    su1_hours DECIMAL(4,2) NOT NULL CHECK (su1_hours >= 0 AND su1_hours <= 24),
    m1_hours DECIMAL(4,2) NOT NULL CHECK (m1_hours >= 0 AND m1_hours <= 24),
    t1_hours DECIMAL(4,2) NOT NULL CHECK (t1_hours >= 0 AND t1_hours <= 24),
    w1_hours DECIMAL(4,2) NOT NULL CHECK (w1_hours >= 0 AND w1_hours <= 24),
    th1_hours DECIMAL(4,2) NOT NULL CHECK (th1_hours >= 0 AND th1_hours <= 24),
    f1_hours DECIMAL(4,2) NOT NULL CHECK (f1_hours >= 0 AND f1_hours <= 24),
    sa1_hours DECIMAL(4,2) NOT NULL CHECK (sa1_hours >= 0 AND sa1_hours <= 24),
    su2_hours DECIMAL(4,2) NOT NULL CHECK (su2_hours >= 0 AND su2_hours <= 24), 
    m2_hours DECIMAL(4,2) NOT NULL CHECK (m2_hours >= 0 AND m2_hours <= 24),
    t2_hours DECIMAL(4,2) NOT NULL CHECK (t2_hours >= 0 AND t2_hours <= 24),
    w2_hours DECIMAL(4,2) NOT NULL CHECK (w2_hours >= 0 AND w2_hours <= 24),
    th2_hours DECIMAL(4,2) NOT NULL CHECK (th2_hours >= 0 AND th2_hours <= 24),
    f2_hours DECIMAL(4,2) NOT NULL CHECK (f2_hours >= 0 AND f2_hours <= 24),
    sa2_hours DECIMAL(4,2) NOT NULL CHECK (sa2_hours >= 0 AND sa2_hours <= 24)    
);

CREATE INDEX idx_timesheet_entries_archive_emp_id_pp_id ON timesheet_entries_archive(employee_id, pay_period_id);
CREATE INDEX idx_timesheet_entries_archive_work_code_id ON timesheet_entries_archive(work_code_id);
CREATE INDEX idx_timesheet_entries_archive_account_code_id ON timesheet_entries_archive(account_code_id);

CREATE TABLE timesheet_entries_in_out_archive (
    timeshee_id SERIAL PRIMARY KEY,
    su1_in_time varchar(12),
    m1_in_time varchar(12),
    t1_in_time varchar(12),
    w1_in_time varchar(12),
    th1_in_time varchar(12),
    f1_in_time varchar(12),
    sa1_in_time varchar(12),
    su2_in_time varchar(12), 
    m2_in_time varchar(12),
    t2_in_time varchar(12),
    w2_in_time varchar(12),
    th2_in_time varchar(12),
    f2_in_time varchar(12),
    sa2_in_time varchar(12),
    su1_out_time varchar(12),
    m1_out_time varchar(12),
    t1_out_time varchar(12),
    w1_out_time varchar(12),
    th1_out_time varchar(12),
    f1_out_time varchar(12),
    sa1_out_time varchar(12),
    su2_out_time varchar(12), 
    m2_out_time varchar(12),
    t2_out_time varchar(12),
    w2_out_time varchar(12),
    th2_out_time varchar(12),
    f2_out_time varchar(12),
    sa2_out_time varchar(12)
);

CREATE INDEX idx_timesheet_entries_in_out_archive_timeshee_id ON timesheet_entries_in_out_archive(timeshee_id);

CREATE TABLE timesheet_entries_comments_archive (
    timesheet_entries_comments_id SERIAL PRIMARY KEY,
    timeshee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    entry_day INTEGER NOT NULL,
    comments TEXT
);

CREATE INDEX idx_timesheet_entries_comments_archive_id ON timesheet_entries_comments_archive(timesheet_entries_comments_id);
CREATE INDEX idx_timesheet_entries_comments_archive_timeshee_id ON timesheet_entries_comments_archive(timeshee_id);


CREATE TABLE timesheet_remarks_archive (
    timesheet_remarks_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet_entries_archive(timeshee_id),
    remarks TEXT,
    remarks_order INTEGER,
    created_at timestamp(6) NULL,
    created_by INTEGER
);

CREATE INDEX idx_timesheet_remarks_archive_timesheet_id ON timesheet_remarks_archive(timesheet_id);

CREATE TABLE timesheet_audit_log_archive (
    timesheet_audit_log_id SERIAL PRIMARY KEY,
    timeshee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    created_at timestamp(6) NULL,
    created_by INTEGER,
    operation_type_id VARCHAR(50) NOT NULL
);

CREATE INDEX idx_timesheet_audit_log_archive_timeshee_id ON timesheet_audit_log_archive(timeshee_id);
CREATE INDEX idx_timesheet_audit_log_archive_action_timestamp ON timesheet_audit_log_archive(created_at);

CREATE TABLE timesheet_summary_archive (
    timesheet_summary_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id),
    total_hours DECIMAL(5,2) NOT NULL CHECK (total_hours >= 0 AND total_hours <= 168),
    status int2 NULL
);

CREATE INDEX idx_timesheet_summary_archive_employee_id ON timesheet_summary_archive(employee_id);
CREATE INDEX idx_timesheet_summary_archive_pay_period_id ON timesheet_summary_archive(pay_period_id);
CREATE INDEX idx_timesheet_summary_archive_status ON timesheet_summary_archive(status);
CREATE UNIQUE INDEX idx_unique_employee_pay_period_archive ON timesheet_summary_archive(employee_id, pay_period_id);
CREATE INDEX idx_timesheet_summary_archive_total_hours ON timesheet_summary_archive(total_hours);

CREATE TABLE timesheet_approvals_archive (
    timesheet_approval_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet_entries_archive(timeshee_id),
    approver_id INTEGER NOT NULL REFERENCES employees(employee_id),
    approval_status int2 NULL,
    approval_date timestamp(6) NULL,
    comments TEXT
);

CREATE INDEX idx_timesheet_approvals_archive_timesheet_id ON timesheet_approvals_archive(timesheet_id);
CREATE INDEX idx_timesheet_approvals_archive_approver_id ON timesheet_approvals_archive(approver_id);
CREATE INDEX idx_timesheet_approvals_archive_approval_status ON timesheet_approvals_archive(approval_status);
CREATE INDEX idx_timesheet_approvals_archive_approval_date ON timesheet_approvals_archive(approval_date);
CREATE UNIQUE INDEX idx_unique_timesheet_approver_archive ON timesheet_approvals_archive(timesheet_id, approver_id);

CREATE TABLE timesheet_notifications_archive (
    timesheet_notification_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet_entries_archive(timeshee_id),
    recipient_id INTEGER NOT NULL REFERENCES employees(employee_id),
    notification_type VARCHAR(50) NOT NULL,
    sent_at timestamp(6) NULL,
    status int2 NULL
);

CREATE INDEX idx_timesheet_notifications_archive_timesheet_id ON timesheet_notifications_archive(timesheet_id);
CREATE INDEX idx_timesheet_notifications_archive_recipient_id ON timesheet_notifications_archive(recipient_id);

-- Additional tables for timesheet functionality
CREATE TABLE timesheet_summary (
    timesheet_summary_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id),
    total_hours DECIMAL(5,2) NOT NULL CHECK (total_hours >= 0 AND total_hours <= 168),
    operation_type_id INTEGER NULL
);  
-- Index for faster queries
CREATE INDEX idx_timesheet_summary_employee_id ON timesheet_summary(employee_id);
CREATE INDEX idx_timesheet_summary_pay_period_id ON timesheet_summary(pay_period_id);
CREATE INDEX idx_timesheet_summary_status ON timesheet_summary(operation_type_id);
CREATE UNIQUE INDEX idx_unique_employee_pay_period ON timesheet_summary(employee_id, pay_period_id);

CREATE TABLE timesheet_approvals (
    timesheet_approval_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet_entries(timesheet_id),
    approver_id INTEGER NOT NULL REFERENCES employees(employee_id),
    operation_type_id INTEGER NULL, -- New column for operation type - approval, rejection, etc.
    approval_date timestamp(6) NULL,
    comments TEXT
);
-- Index for faster queries
CREATE INDEX idx_timesheet_approvals_timesheet_id ON timesheet_approvals(timesheet_id);
CREATE INDEX idx_timesheet_approvals_approver_id ON timesheet_approvals(approver_id);
CREATE UNIQUE INDEX idx_unique_timesheet_approver ON timesheet_approvals(timesheet_id, approver_id);


CREATE TABLE timesheet_notifications (
    timesheet_notification_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet_entries(timesheet_id),
    recipient_id INTEGER NOT NULL REFERENCES employees(employee_id),
    notification_type VARCHAR(50) NOT NULL,
    sent_at timestamp(6) NULL,
    status int2 NULL
);
-- Index for faster queries
CREATE INDEX idx_timesheet_notifications_timesheet_id ON timesheet_notifications(timesheet_id);
CREATE INDEX idx_timesheet_notifications_recipient_id ON timesheet_notifications(recipient_id);

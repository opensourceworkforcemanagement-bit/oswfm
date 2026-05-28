-- Consolidated timesheet tables from timesheetservice V2-V5

-- ============================================================================
-- V2: Core timesheet tables
-- ============================================================================

CREATE TABLE timesheet_types (
    timesheet_type_id SERIAL PRIMARY KEY,
    timesheet_type_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE pay_period_types (
    pay_period_type_id SERIAL PRIMARY KEY,
    pay_period_type_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE INDEX idx_pay_period_types_name ON pay_period_types(pay_period_type_name);

CREATE TABLE pay_periods (
    pay_period_id  SERIAL PRIMARY KEY,
    pay_period_type_id INTEGER NOT NULL REFERENCES pay_period_types(pay_period_type_id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    year INTEGER NOT NULL,
    period_number INTEGER NOT NULL
);

CREATE UNIQUE INDEX idx_unique_pay_period ON pay_periods(start_date, end_date);
CREATE INDEX idx_pay_periods_start_date ON pay_periods(start_date);
CREATE INDEX idx_pay_periods_end_date ON pay_periods(end_date);
CREATE INDEX idx_pay_periods_pay_period_id ON pay_periods(pay_period_id);
CREATE INDEX idx_pay_periods_pay_period_type_id ON pay_periods(pay_period_type_id);

CREATE TABLE timesheet (
    timesheet_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id),
    timesheet_type_id INTEGER NOT NULL REFERENCES timesheet_types(timesheet_type_id),
    status int2 NULL
);

CREATE INDEX idx_timesheet_employee_id ON timesheet(employee_id);
CREATE INDEX idx_timesheet_pay_period_id ON timesheet(pay_period_id);

CREATE TABLE timesheet_operations_types (
    operation_type_id SERIAL PRIMARY KEY,
    operation_type_name VARCHAR(50) NOT NULL,
    operation_description TEXT
);

CREATE INDEX idx_timesheet_operations_types_operation_type_name ON timesheet_operations_types(operation_type_name);

CREATE TABLE work_codes (
    work_code_id SERIAL PRIMARY KEY,
    prefix VARCHAR(10),
    suffix VARCHAR(10),
    short_work_code VARCHAR(10) NOT NULL,
    long_work_code VARCHAR(50) NOT NULL,
    description TEXT,
    status int2 NULL,
    effective_date DATE,
    expiration_date DATE
);

CREATE INDEX idx_work_codes_work_code ON work_codes(work_code_id);

CREATE TABLE account_codes (
    account_code_id SERIAL PRIMARY KEY,
    account_code VARCHAR(50) NOT NULL,
    description TEXT,
    status int2 NULL
);

CREATE INDEX idx_account_codes_account_code ON account_codes(account_code_id);

CREATE TABLE task_codes (
    task_code_id SERIAL PRIMARY KEY,
    task_code VARCHAR(50) NOT NULL,
    description TEXT,
    status int2 NULL
);

CREATE INDEX idx_task_codes_task_code ON task_codes(task_code_id);
CREATE INDEX idx_task_codes_status ON task_codes(status);

CREATE TABLE timesheet_entries (
    timesheet_entries_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
    work_code_id INTEGER NOT NULL REFERENCES work_codes(work_code_id),
    account_code_id INTEGER NOT NULL REFERENCES account_codes(account_code_id),
    su1_hours DECIMAL(4,2) NOT NULL,
    m1_hours DECIMAL(4,2) NOT NULL,
    t1_hours DECIMAL(4,2) NOT NULL,
    w1_hours DECIMAL(4,2) NOT NULL,
    th1_hours DECIMAL(4,2) NOT NULL,
    f1_hours DECIMAL(4,2) NOT NULL,
    sa1_hours DECIMAL(4,2) NOT NULL,
    su2_hours DECIMAL(4,2) NOT NULL,
    m2_hours DECIMAL(4,2) NOT NULL,
    t2_hours DECIMAL(4,2) NOT NULL,
    w2_hours DECIMAL(4,2) NOT NULL,
    th2_hours DECIMAL(4,2) NOT NULL,
    f2_hours DECIMAL(4,2) NOT NULL,
    sa2_hours DECIMAL(4,2) NOT NULL
);

CREATE INDEX idx_timesheet_entries_timesheet_id ON timesheet_entries(timesheet_id);
CREATE INDEX idx_timesheet_task_code ON timesheet_entries(work_code_id);
CREATE INDEX idx_timesheet_account_code ON timesheet_entries(account_code_id);

CREATE TABLE timesheet_entries_comments (
    timesheet_entries_comments_id SERIAL PRIMARY KEY,
    timesheet_entries_timesheet_id INTEGER NOT NULL REFERENCES timesheet_entries(timesheet_entries_id),
    entry_day INTEGER NOT NULL,
    comments TEXT
);

CREATE INDEX idx_timesheet_entries_comments_timesheet_entries_comments_id ON timesheet_entries_comments(timesheet_entries_comments_id);
CREATE INDEX idx_timesheet_entries_comments_timesheet_id ON timesheet_entries_comments(timesheet_entries_timesheet_id);

CREATE TABLE timesheet_entries_in_out (
    timesheet_entries_in_out_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
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

CREATE INDEX idx_timesheet_entries_in_out_timesheet_id ON timesheet_entries_in_out(timesheet_id);

CREATE TABLE timesheet_entries_in_out_comments (
    timesheet_entries_in_out_comments_id SERIAL PRIMARY KEY,
    timesheet_entries_in_out_id INTEGER NOT NULL REFERENCES timesheet_entries_in_out(timesheet_entries_in_out_id),
    entry_day INTEGER NOT NULL,
    comments TEXT
);

CREATE INDEX idx_timesheet_entries_in_out_comments_id ON timesheet_entries_in_out_comments(timesheet_entries_in_out_comments_id);
CREATE INDEX idx_timesheet_entries_in_out_comments_timesheet_id ON timesheet_entries_in_out_comments(timesheet_entries_in_out_id);

CREATE TABLE timesheet_remarks (
    timesheet_remarks_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
    remarks TEXT,
    remarks_order INTEGER,
    created_at timestamp(6) NULL,
    created_by INTEGER
);

CREATE INDEX idx_timesheet_remarks_timesheet_id ON timesheet_remarks(timesheet_id);

CREATE TABLE timesheet_audit_log (
    timesheet_audit_log_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
    created_at timestamp(6) NULL,
    created_by INTEGER,
    operation_type_id INTEGER NULL
);

CREATE INDEX idx_timesheet_audit_log_timesheet_id ON timesheet_audit_log(timesheet_id);
CREATE INDEX idx_timesheet_audit_log_action_timestamp ON timesheet_audit_log(created_at);

CREATE TABLE timesheet_summary (
    timesheet_summary_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id),
    total_hours DECIMAL(5,2) NOT NULL CHECK (total_hours >= 0 AND total_hours <= 168),
    operation_type_id INTEGER NULL
);

CREATE INDEX idx_timesheet_summary_employee_id ON timesheet_summary(employee_id);
CREATE INDEX idx_timesheet_summary_pay_period_id ON timesheet_summary(pay_period_id);
CREATE INDEX idx_timesheet_summary_status ON timesheet_summary(operation_type_id);
CREATE UNIQUE INDEX idx_unique_employee_pay_period ON timesheet_summary(employee_id, pay_period_id);

CREATE TABLE timesheet_approvals (
    timesheet_approval_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
    approver_id INTEGER NOT NULL REFERENCES employees(employee_id),
    operation_type_id INTEGER NULL,
    approval_date timestamp(6) NULL,
    comments TEXT
);

CREATE INDEX idx_timesheet_approvals_timesheet_id ON timesheet_approvals(timesheet_id);
CREATE INDEX idx_timesheet_approvals_approver_id ON timesheet_approvals(approver_id);
CREATE UNIQUE INDEX idx_unique_timesheet_approver ON timesheet_approvals(timesheet_id, approver_id);

CREATE TABLE timesheet_notifications (
    timesheet_notification_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
    recipient_id INTEGER NOT NULL REFERENCES employees(employee_id),
    notification_type VARCHAR(50) NOT NULL,
    sent_at timestamp(6) NULL,
    status int2 NULL
);

CREATE INDEX idx_timesheet_notifications_timesheet_id ON timesheet_notifications(timesheet_id);
CREATE INDEX idx_timesheet_notifications_recipient_id ON timesheet_notifications(recipient_id);

-- "Old" (migration-tier) tables

CREATE TABLE timesheet_entries_old (
    timesheet_entries_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    work_code_id INTEGER NOT NULL,
    account_code_id INTEGER NOT NULL,
    su1_hours DECIMAL(4,2) NOT NULL, m1_hours DECIMAL(4,2) NOT NULL, t1_hours DECIMAL(4,2) NOT NULL,
    w1_hours DECIMAL(4,2) NOT NULL, th1_hours DECIMAL(4,2) NOT NULL, f1_hours DECIMAL(4,2) NOT NULL,
    sa1_hours DECIMAL(4,2) NOT NULL, su2_hours DECIMAL(4,2) NOT NULL, m2_hours DECIMAL(4,2) NOT NULL,
    t2_hours DECIMAL(4,2) NOT NULL, w2_hours DECIMAL(4,2) NOT NULL, th2_hours DECIMAL(4,2) NOT NULL,
    f2_hours DECIMAL(4,2) NOT NULL, sa2_hours DECIMAL(4,2) NOT NULL
);

CREATE INDEX idx_timesheet_entries_old_work_code_id ON timesheet_entries_old(work_code_id);
CREATE INDEX idx_timesheet_entries_old_account_code ON timesheet_entries_old(account_code_id);

CREATE TABLE timesheet_entries_in_out_old (
    timesheet_entries_timesheet_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    su1_in_time varchar(12), m1_in_time varchar(12), t1_in_time varchar(12),
    w1_in_time varchar(12), th1_in_time varchar(12), f1_in_time varchar(12),
    sa1_in_time varchar(12), su2_in_time varchar(12), m2_in_time varchar(12),
    t2_in_time varchar(12), w2_in_time varchar(12), th2_in_time varchar(12),
    f2_in_time varchar(12), su1_out_time varchar(12), m1_out_time varchar(12),
    t1_out_time varchar(12), w1_out_time varchar(12), th1_out_time varchar(12),
    f1_out_time varchar(12), sa1_out_time varchar(12), su2_out_time varchar(12),
    m2_out_time varchar(12), t2_out_time varchar(12), w2_out_time varchar(12),
    th2_out_time varchar(12), f2_out_time varchar(12), sa2_out_time varchar(12)
);

CREATE INDEX idx_timesheet_entries_in_out_old_timesheet_id ON timesheet_entries_in_out_old(timesheet_id);

CREATE TABLE timesheet_entries_comments_old (
    timesheet_entries_comments_id  INTEGER NOT NULL,
    timesheet_entries_timesheet_id INTEGER NOT NULL,
    entry_day INTEGER NOT NULL,
    comments TEXT
);

CREATE INDEX idx_timesheet_entries_comments_old_timesheet_entries_comments_id ON timesheet_entries_comments_old(timesheet_entries_comments_id);
CREATE INDEX idx_timesheet_entries_comments_old_timesheet_id ON timesheet_entries_comments_old(timesheet_entries_timesheet_id);

CREATE TABLE timesheet_remarks_old (
    timesheet_remarks_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    remarks TEXT,
    remarks_order INTEGER,
    created_at timestamp(6) NULL,
    created_by INTEGER
);

CREATE INDEX idx_timesheet_remarks_old_timesheet_id ON timesheet_remarks_old(timesheet_id);

CREATE TABLE timesheet_old_audit_log (
    timesheet_audit_log_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    created_at timestamp(6) NULL,
    created_by INTEGER,
    operation_type_id INTEGER NULL
);

CREATE INDEX idx_timesheet_old_audit_log_timesheet_id ON timesheet_old_audit_log(timesheet_id);
CREATE INDEX idx_timesheet_old_audit_log_action_timestamp ON timesheet_old_audit_log(created_at);

-- Archive tables

CREATE TABLE timesheet_entries_archive (
    timesheet_entries_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
    work_code_id INTEGER NOT NULL REFERENCES work_codes(work_code_id),
    account_code_id INTEGER NOT NULL REFERENCES account_codes(account_code_id),
    su1_hours DECIMAL(4,2) NOT NULL, m1_hours DECIMAL(4,2) NOT NULL, t1_hours DECIMAL(4,2) NOT NULL,
    w1_hours DECIMAL(4,2) NOT NULL, th1_hours DECIMAL(4,2) NOT NULL, f1_hours DECIMAL(4,2) NOT NULL,
    sa1_hours DECIMAL(4,2) NOT NULL, su2_hours DECIMAL(4,2) NOT NULL, m2_hours DECIMAL(4,2) NOT NULL,
    t2_hours DECIMAL(4,2) NOT NULL, w2_hours DECIMAL(4,2) NOT NULL, th2_hours DECIMAL(4,2) NOT NULL,
    f2_hours DECIMAL(4,2) NOT NULL, sa2_hours DECIMAL(4,2) NOT NULL
);

CREATE INDEX idx_timesheet_entries_archive_work_code_id ON timesheet_entries_archive(work_code_id);
CREATE INDEX idx_timesheet_entries_archive_account_code ON timesheet_entries_archive(account_code_id);

CREATE TABLE timesheet_entries_in_out_archive (
    timesheet_entries_in_out_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    su1_in_time varchar(12), m1_in_time varchar(12), t1_in_time varchar(12),
    w1_in_time varchar(12), th1_in_time varchar(12), f1_in_time varchar(12),
    sa1_in_time varchar(12), su2_in_time varchar(12), m2_in_time varchar(12),
    t2_in_time varchar(12), w2_in_time varchar(12), th2_in_time varchar(12),
    f2_in_time varchar(12), su1_out_time varchar(12), m1_out_time varchar(12),
    t1_out_time varchar(12), w1_out_time varchar(12), th1_out_time varchar(12),
    f1_out_time varchar(12), sa1_out_time varchar(12), su2_out_time varchar(12),
    m2_out_time varchar(12), t2_out_time varchar(12), w2_out_time varchar(12),
    th2_out_time varchar(12), f2_out_time varchar(12), sa2_out_time varchar(12)
);

CREATE INDEX idx_timesheet_entries_in_out_archive_timesheet_id ON timesheet_entries_in_out_archive(timesheet_id);

CREATE TABLE timesheet_entries_comments_archive (
    timesheet_entries_comments_id INTEGER NOT NULL,
    timesheet_entries_timesheet_id INTEGER NOT NULL,
    entry_day INTEGER NOT NULL,
    comments TEXT
);

CREATE INDEX idx_timesheet_entries_comments_archive_timesheet_entries_comments_id ON timesheet_entries_comments_archive(timesheet_entries_comments_id);
CREATE INDEX idx_timesheet_entries_comments_archive_timesheet_id ON timesheet_entries_comments_archive(timesheet_entries_timesheet_id);

CREATE TABLE timesheet_remarks_archive (
    timesheet_remarks_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    remarks TEXT,
    remarks_order INTEGER,
    created_at timestamp(6) NULL,
    created_by INTEGER
);

CREATE INDEX idx_timesheet_remarks_archive_timesheet_id ON timesheet_remarks_archive(timesheet_id);

CREATE TABLE timesheet_archive_audit_log (
    timesheet_audit_log_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    created_at timestamp(6) NULL,
    created_by INTEGER,
    operation_type_id INTEGER NULL
);

CREATE INDEX idx_timesheet_archive_audit_log_timesheet_id ON timesheet_archive_audit_log(timesheet_id);
CREATE INDEX idx_timesheet_archive_audit_log_action_timestamp ON timesheet_archive_audit_log(created_at);

CREATE TABLE timesheet_summary_archive (
    timesheet_summary_id INTEGER NOT NULL,
    employee_id INTEGER NOT NULL,
    pay_period_id INTEGER NOT NULL,
    total_hours DECIMAL(5,2) NOT NULL,
    status int2 NULL
);

CREATE INDEX idx_timesheet_summary_archive_employee_id ON timesheet_summary_archive(employee_id);
CREATE INDEX idx_timesheet_summary_archive_pay_period_id ON timesheet_summary_archive(pay_period_id);
CREATE INDEX idx_timesheet_summary_archive_status ON timesheet_summary_archive(status);
CREATE UNIQUE INDEX idx_unique_employee_pay_period_archive ON timesheet_summary_archive(employee_id, pay_period_id);
CREATE INDEX idx_timesheet_summary_archive_total_hours ON timesheet_summary_archive(total_hours);

CREATE TABLE timesheet_approvals_archive (
    timesheet_approval_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    approver_id INTEGER NOT NULL,
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
    timesheet_notification_id INTEGER NOT NULL,
    timesheet_id INTEGER NOT NULL,
    recipient_id INTEGER NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    sent_at timestamp(6) NULL,
    status int2 NULL
);

CREATE INDEX idx_timesheet_notifications_archive_timesheet_id ON timesheet_notifications_archive(timesheet_id);
CREATE INDEX idx_timesheet_notifications_archive_recipient_id ON timesheet_notifications_archive(recipient_id);

-- ============================================================================
-- V3: Normalized timesheet strategy tables
-- ============================================================================

CREATE TABLE timesheet_normalized (
    timesheet_normalized_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id),
    timesheet_type_id INTEGER NOT NULL,
    status int2 NULL
);

CREATE TABLE timesheet_entries_normalized (
    timesheet_entries_id SERIAL PRIMARY KEY,
    timesheet_normalized_id INTEGER NOT NULL,
    CONSTRAINT fk_timesheet FOREIGN KEY (timesheet_normalized_id) REFERENCES timesheet_normalized(timesheet_normalized_id)
);

CREATE TABLE code_types (
    code_type_id SERIAL PRIMARY KEY,
    code_type_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE workforce_codes (
    id SERIAL PRIMARY KEY,
    code_id INTEGER NOT NULL,
    code_type_id INTEGER NOT NULL,
    prefix VARCHAR(10),
    suffix VARCHAR(10),
    short_code_value VARCHAR(10) NOT NULL,
    long_code_value VARCHAR(255) NOT NULL,
    description TEXT,
    status int2 NULL,
    effective_date DATE,
    expiration_date DATE
);

CREATE INDEX idx_workforce_codes_code_type ON workforce_codes(code_type_id);
CREATE INDEX idx_workforce_codes_code_id ON workforce_codes(code_id);
CREATE INDEX idx_workforce_codes_status ON workforce_codes(status);

CREATE TABLE timesheet_entry_codes (
    timesheet_entries_id INTEGER NOT NULL,
    workforce_codes_id INTEGER NOT NULL,
    PRIMARY KEY (timesheet_entries_id, workforce_codes_id),
    CONSTRAINT fk_timesheet_entry_codes_workforce FOREIGN KEY (workforce_codes_id)
        REFERENCES workforce_codes(id) ON DELETE CASCADE,
    CONSTRAINT fk_timesheet_entry_codes_entry FOREIGN KEY (timesheet_entries_id)
        REFERENCES timesheet_entries_normalized(timesheet_entries_id) ON DELETE CASCADE
);

CREATE TABLE timesheet_entry_minutes (
    id SERIAL PRIMARY KEY,
    timesheet_entries_id INTEGER NOT NULL,
    minutes NUMERIC(5, 2) NOT NULL,
    day_of_week char(1),
    date DATE,
    CONSTRAINT fk_timesheet_entry_minutes FOREIGN KEY (timesheet_entries_id)
        REFERENCES timesheet_entries_normalized(timesheet_entries_id) ON DELETE CASCADE
);

CREATE INDEX idx_timesheet_entries_normalized_timesheet_normalized_id ON timesheet_entries_normalized(timesheet_normalized_id);
CREATE INDEX idx_timesheet_entry_codes_workforce_id ON timesheet_entry_codes(workforce_codes_id);
CREATE INDEX idx_timesheet_entry_minutes_entry_id ON timesheet_entry_minutes(timesheet_entries_id);

-- ============================================================================
-- V4: Seed data for pay period types, timesheet types, and workforce codes
-- ============================================================================

INSERT INTO pay_period_types (pay_period_type_name, description) VALUES
    ('Weekly', 'Pay period that occurs every week (7 days)'),
    ('Bi-Weekly', 'Pay period that occurs every two weeks (14 days)'),
    ('Semi-Monthly', 'Pay period that occurs twice a month (typically 1st-15th and 16th-end of month)'),
    ('Monthly', 'Pay period that occurs once a month'),
    ('Quarterly', 'Pay period that occurs every quarter (3 months)'),
    ('Annually', 'Pay period that occurs once a year (12 months)'),
    ('Daily', 'Pay period that occurs every day (24 hours)'),
    ('Bi-Monthly', 'Pay period that occurs every two months (60 days)'),
    ('Tri-Weekly', 'Pay period that occurs every three weeks (21 days)'),
    ('Four-Weekly', 'Pay period that occurs every four weeks (28 days)'),
    ('Custom', 'Custom defined pay period type'),
    ('Seasonal', 'Pay period that occurs based on seasonal work patterns');

INSERT INTO timesheet_types (timesheet_type_name, description) VALUES
    ('Regular', 'Standard timesheet for regular working hours'),
    ('Corrective', 'Timesheet for correcting previously submitted hours'),
    ('Overtime', 'Timesheet for tracking overtime hours'),
    ('Project', 'Timesheet for project-based work tracking'),
    ('Leave', 'Timesheet for tracking leave/PTO hours'),
    ('Training', 'Timesheet for training and development hours'),
    ('On-Call', 'Timesheet for on-call duty hours'),
    ('Comp Time', 'Timesheet for compensatory time tracking'),
    ('Freelance', 'Timesheet for freelance or contract work hours'),
    ('Internship', 'Timesheet for internship program hours'),
    ('Volunteer', 'Timesheet for volunteer work hours'),
    ('Remote Work', 'Timesheet for remote work hours'),
    ('Shift Work', 'Timesheet for shift-based work hours'),
    ('Field Work', 'Timesheet for fieldwork hours'),
    ('Consulting', 'Timesheet for consulting service hours'),
    ('Research', 'Timesheet for research project hours'),
    ('Audit', 'Timesheet for audit-related work hours'),
    ('Maintenance', 'Timesheet for maintenance and support hours'),
    ('Emergency', 'Timesheet for emergency response hours'),
    ('Holiday', 'Timesheet for holiday work hours'),
    ('Travel', 'Timesheet for travel-related work hours'),
    ('Administrative', 'Timesheet for administrative tasks hours'),
    ('Creative', 'Timesheet for creative project hours'),
    ('Technical', 'Timesheet for technical support hours'),
    ('Management', 'Timesheet for management and supervisory hours'),
    ('Customer Support', 'Timesheet for customer support hours'),
    ('Sales', 'Timesheet for sales-related work hours'),
    ('Marketing', 'Timesheet for marketing campaign hours'),
    ('Development', 'Timesheet for software development hours'),
    ('Testing', 'Timesheet for quality assurance and testing hours'),
    ('Documentation', 'Timesheet for documentation and writing hours'),
    ('Planning', 'Timesheet for planning and strategy hours'),
    ('Review', 'Timesheet for review and feedback hours'),
    ('Budgeting', 'Timesheet for budgeting and financial hours'),
    ('Compliance', 'Timesheet for compliance-related work hours'),
    ('Security', 'Timesheet for security-related work hours'),
    ('Health & Safety', 'Timesheet for health and safety-related hours'),
    ('Environmental', 'Timesheet for environmental project hours'),
    ('Community Service', 'Timesheet for community service hours'),
    ('Event', 'Timesheet for event planning and execution hours'),
    ('Other', 'Timesheet for other types of work hours not specified'),
    ('Special Project', 'Timesheet for special project work hours');

INSERT INTO timesheet_operations_types (operation_type_name, operation_description) VALUES
    ('CREATED', 'Timesheet was created'),
    ('SUBMITTED', 'Timesheet was submitted for approval'),
    ('APPROVED', 'Timesheet was approved'),
    ('REJECTED', 'Timesheet was rejected'),
    ('REVISED', 'Timesheet was revised/modified'),
    ('RECALLED', 'Timesheet was recalled by employee'),
    ('ARCHIVED', 'Timesheet was archived'),
    ('DELETED', 'Timesheet was deleted');

INSERT INTO code_types (code_type_name, description) VALUES
    ('WORK_CODE', 'Code representing different types of work activities'),
    ('PROJECT_CODE', 'Code representing different projects'),
    ('DEPARTMENT_CODE', 'Code representing different departments within the organization'),
    ('LEAVE_CODE', 'Code representing different types of leave or time off'),
    ('PREMIUM_PAY_CODE', 'Code representing premium pay categories such as overtime or holiday pay'),
    ('ACCOUNT_CODE', 'Code representing accounting codes');

INSERT INTO workforce_codes (code_type_id, code_id, short_code_value, long_code_value, description, status, effective_date) VALUES
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'WORK_CODE'), 101, 'DEV', 'Development Work', 'Work related to software development tasks', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'WORK_CODE'), 102, 'MTN', 'Maintenance Work', 'Work related to system maintenance tasks', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'PROJECT_CODE'), 201, 'PRJ001', 'Project Alpha', 'Code for Project Alpha initiatives', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'PROJECT_CODE'), 202, 'PRJ002', 'Project Beta', 'Code for Project Beta initiatives', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'DEPARTMENT_CODE'), 301, 'HR', 'Human Resources Department', 'Code for Human Resources department activities', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'DEPARTMENT_CODE'), 302, 'IT', 'Information Technology Department', 'Code for IT department activities', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'LEAVE_CODE'), 401, 'VAC', 'Vacation Leave', 'Code for vacation leave taken by employees', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'LEAVE_CODE'), 402, 'SICK', 'Sick Leave', 'Code for sick leave taken by employees', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'PREMIUM_PAY_CODE'), 501, 'OT', 'Overtime Pay', 'Code for overtime pay rates', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'PREMIUM_PAY_CODE'), 502, 'HOL', 'Holiday Pay', 'Code for holiday pay rates', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'ACCOUNT_CODE'), 601, 'ACC', 'Universal Kitchen Group Account', 'Code Universal Kitchen Group Account', 1, CURRENT_DATE),
    ((SELECT code_type_id FROM code_types WHERE code_type_name = 'ACCOUNT_CODE'), 602, 'ACC', 'Universal Kamera Group Account', 'Code Universal Kamera Group Account', 1, CURRENT_DATE);

-- ============================================================================
-- V5: Normalized summary view
-- ============================================================================

CREATE OR REPLACE VIEW timesheet_normalized_summary AS
SELECT
    tn.timesheet_normalized_id  AS timesheet_summary_id,
    tn.employee_id,
    tn.pay_period_id,
    COALESCE(
        (SELECT SUM(tem.minutes) / 60.0
         FROM timesheet_entries_normalized ten
         JOIN timesheet_entry_minutes tem ON tem.timesheet_entries_id = ten.timesheet_entries_id
         WHERE ten.timesheet_normalized_id = tn.timesheet_normalized_id
        ), 0
    )::DECIMAL(5,2)             AS total_hours,
    tn.status                   AS operation_type_id
FROM timesheet_normalized tn;

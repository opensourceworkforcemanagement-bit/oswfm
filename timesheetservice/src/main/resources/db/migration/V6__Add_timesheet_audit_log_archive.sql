CREATE TABLE timesheet_audit_log_archive (
    timesheet_audit_log_id SERIAL PRIMARY KEY,
    timesheet_id INTEGER NOT NULL,
    created_at timestamp(6) NULL,
    created_by INTEGER,
    operation_type_id VARCHAR(50) NULL
);

CREATE INDEX idx_timesheet_audit_log_archive_timesheet_id ON timesheet_audit_log_archive(timesheet_id);
CREATE INDEX idx_timesheet_audit_log_archive_created_at ON timesheet_audit_log_archive(created_at);

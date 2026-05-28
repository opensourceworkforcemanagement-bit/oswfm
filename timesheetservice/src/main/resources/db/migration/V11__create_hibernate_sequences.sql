-- Hibernate 6 GenerationType.SEQUENCE without @SequenceGenerator expects a sequence
-- named <table_name>_seq. PostgreSQL SERIAL creates <table>_<col>_seq, not <table>_seq.
-- Create the sequences Hibernate needs for all timesheet entities.

CREATE SEQUENCE IF NOT EXISTS account_codes_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS task_codes_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS work_codes_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS pay_periods_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_comments_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_in_out_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_remarks_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_audit_log_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_approvals_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_notifications_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_operations_types_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_normalized_summary_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_old_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_in_out_old_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_comments_old_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_remarks_old_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_old_audit_log_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_archive_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_in_out_archive_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_entries_comments_archive_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_remarks_archive_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_audit_log_archive_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_summary_archive_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_approvals_archive_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS timesheet_notifications_archive_seq START 1 INCREMENT 50;

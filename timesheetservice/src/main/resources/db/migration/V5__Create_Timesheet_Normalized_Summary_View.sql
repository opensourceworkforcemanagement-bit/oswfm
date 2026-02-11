-- View: timesheet_normalized_summary
-- Mirrors the timesheet_summary table structure but derives data from the
-- timesheet_normalized table and its related entries.
-- total_hours is computed by summing timesheet_entry_minutes (stored as minutes, converted to hours).
-- operation_type_id is mapped from timesheet_normalized.status.

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

-- Timesheet Normalized Strategy Database Schema
-- PostgreSQL

CREATE TABLE timesheet_normalized (
    timesheet_normalized_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    pay_period_id INTEGER NOT NULL REFERENCES pay_periods(pay_period_id),
    timesheet_type_id INTEGER NOT NULL,
    status int2 NULL
);

--Main timesheet entries table
CREATE TABLE timesheet_entries_normalized  (
    timesheet_entries_id SERIAL PRIMARY KEY,
    timesheet_normalized_id INTEGER NOT NULL,
    CONSTRAINT fk_timesheet FOREIGN KEY (timesheet_normalized_id) REFERENCES timesheet_normalized(timesheet_normalized_id)
);

CREATE TABLE workforce_codes (
    id SERIAL PRIMARY KEY,
    code_type VARCHAR(50) NOT NULL,
    code_id INTEGER NOT NULL,
    short_code_value VARCHAR(10) NOT NULL,
    long_code_value VARCHAR(255) NOT NULL,
    description TEXT,
    status int2 NULL,
    effective_date DATE,
    expiration_date DATE
);
-- Index for faster queries
CREATE INDEX idx_workforce_codes_code_type ON workforce_codes(code_type);
CREATE INDEX idx_workforce_codes_code_id ON workforce_codes(code_id);
CREATE INDEX idx_workforce_codes_status ON workforce_codes(status); 

-- Codes associated with timesheet entries
CREATE TABLE timesheet_entry_codes (
    timesheet_entries_id INTEGER NOT NULL,
    workforce_codes_id INTEGER NOT NULL,
    PRIMARY KEY (timesheet_entries_id, workforce_codes_id),
    CONSTRAINT fk_timesheet_entry_codes_workforce FOREIGN KEY (workforce_codes_id)
        REFERENCES workforce_codes(id) ON DELETE CASCADE,
    CONSTRAINT fk_timesheet_entry_codes_entry FOREIGN KEY (timesheet_entries_id)
        REFERENCES timesheet_entries_normalized(timesheet_entries_id) ON DELETE CASCADE
);

-- minutes by date for timesheet entries
CREATE TABLE timesheet_entry_minutes (
    id SERIAL PRIMARY KEY,
    timesheet_entries_id INTEGER NOT NULL,
    minutes NUMERIC(5, 2) NOT NULL,
    day_of_week char(1), -- 'SU - 0 'M' - 1, 'T' - 2, 'W' - 3, 'R' - 4, 'F' - 5, 'S' - 6
    date DATE,
    CONSTRAINT fk_timesheet_entry_minutes FOREIGN KEY (timesheet_entries_id) 
        REFERENCES timesheet_entries_normalized(timesheet_entries_id) ON DELETE CASCADE    
);

-- Indexes for performance
CREATE INDEX idx_timesheet_entries_normalized_timesheet_normalized_id ON timesheet_entries_normalized(timesheet_normalized_id);
CREATE INDEX idx_timesheet_entry_codes_workforce_id ON timesheet_entry_codes(workforce_codes_id);
CREATE INDEX idx_timesheet_entry_minutes_entry_id ON timesheet_entry_minutes(timesheet_entries_id);


-- Sample data (optional)
-- INSERT INTO timesheet_entries (timesheet_id) VALUES (1);
-- INSERT INTO timesheet_entry_codes (timesheet_entries_id, code_type, code_id, code_value) 
--     VALUES (1, 'WORK_CODE', 101, 'Development');
-- INSERT INTO timesheet_entry_minutes (timesheet_entries_id, date, minutes) 
--     VALUES (1, '2025-01-06', 8.0);

-- Remove user_id column from employees table (was added in V2, superseded by join table)
ALTER TABLE employees DROP COLUMN IF EXISTS user_id;

-- Create employee_user join table
CREATE TABLE IF NOT EXISTS employee_user (
    employee_user_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id)
);
-- Index for faster queries
CREATE INDEX idx_employee_user_employee_id ON employee_user(employee_id);
CREATE INDEX idx_employee_user_user_id ON employee_user(user_id);

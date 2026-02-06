-- Seed data for pay_period_types table
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

-- Seed data for timesheet_types table
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
    ('Training', 'Timesheet for training and development hours'),
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

-- Seed data for timesheet_operations_types table
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

-- Seed data for workforce_codes table

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

    
CREATE TABLE IF NOT EXISTS employees (
	employee_id SERIAL PRIMARY KEY,
	employee_identifier varchar(255) NOT NULL,
	first_name varchar(255) NULL,
    middle_name varchar(255) NULL,
	last_name varchar(255) NULL,
	status int2 NULL,
	user_name varchar(255) NOT NULL
);
-- Index for faster queries
CREATE INDEX idx_employees_employee_identifier ON employees(employee_identifier);
CREATE INDEX idx_employees_user_name ON employees(user_name);
CREATE INDEX idx_employees_status ON employees(status);

CREATE TABLE IF NOT EXISTS employees_ssn (
    employee_ssn_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    ssn VARCHAR(11) NOT NULL
); 
-- Index for faster queries
CREATE INDEX idx_employees_ssn_employee_id ON employees_ssn(employee_id);
CREATE INDEX idx_employees_ssn_ssn ON employees_ssn(ssn);

CREATE TABLE IF NOT EXISTS employees_status_history (
    employee_status_history_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    status int2 NOT NULL,
    changed_at timestamp(6) NULL,
    changed_by_employee_id INTEGER
);

-- Index for faster queries
CREATE INDEX idx_employees_status_history_employee_id ON employees_status_history(employee_id);
CREATE INDEX idx_employees_status_history_status ON employees_status_history(status);
CREATE INDEX idx_employees_status_history_changed_at ON employees_status_history(changed_at);

CREATE TABLE IF NOT EXISTS employees_audit_log (
    employee_audit_log_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    action VARCHAR(50) NOT NULL,
    action_timestamp timestamp(6) NULL,
    action_by INTEGER
);

-- Index for faster queries
CREATE INDEX idx_employees_audit_log_employee_id ON employees_audit_log(employee_id);
CREATE INDEX idx_employees_audit_log_action ON employees_audit_log(action);

CREATE TABLE IF NOT EXISTS employees_settings (
    employee_setting_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    setting_key VARCHAR(50) NOT NULL,
    setting_description TEXT
);

-- Index for faster queries
CREATE INDEX idx_employees_settings_employee_id ON employees_settings(employee_id);
CREATE INDEX idx_employees_settings_setting_key ON employees_settings(setting_key);

CREATE TABLE IF NOT EXISTS employees_preferences (
    employee_preference_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    preference_key VARCHAR(50) NOT NULL,
    preference_description TEXT
);

-- Index for faster queries
CREATE INDEX idx_employees_preferences_employee_id ON employees_preferences(employee_id);
CREATE INDEX idx_employees_preferences_preference_key ON employees_preferences(preference_key);

CREATE TABLE IF NOT EXISTS email_addresses (
    email_address_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    email VARCHAR(100) NOT NULL,
    type int2 NOT NULL, -- e.g., personal, work, contact
    is_primary BOOLEAN DEFAULT FALSE
);
-- Index for faster queries
CREATE INDEX idx_email_addresses_employee_id ON email_addresses(employee_id);

CREATE TABLE IF NOT EXISTS phone_numbers (
    phone_number_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
    phone_number VARCHAR(15) NOT NULL,
    type int2 NOT NULL, -- e.g., mobile, home, work
    is_primary BOOLEAN DEFAULT FALSE
);
-- Index for faster queries
CREATE INDEX idx_phone_numbers_employee_id ON phone_numbers(employee_id);
CREATE INDEX idx_phone_numbers_phone_number ON phone_numbers(phone_number);
CREATE INDEX idx_phone_numbers_type ON phone_numbers(type);

CREATE TABLE IF NOT EXISTS addresses (
    address_id SERIAL PRIMARY KEY,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE
);
-- Index for faster queries
CREATE INDEX idx_addresses_city ON addresses(city);
CREATE INDEX idx_addresses_state ON addresses(state);
CREATE INDEX idx_addresses_zip_code ON addresses(zip_code);
CREATE INDEX idx_addresses_country ON addresses(country);

CREATE TABLE IF NOT EXISTS employee_address (
    address_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id)
);
-- Index for faster queries
CREATE INDEX idx_employee_address_employee_id ON employee_address(employee_id);

CREATE TABLE IF NOT EXISTS employees_emergency_contacts (
    emergency_contact_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id),
	first_name varchar(255) NULL,
    middle_name varchar(255) NULL,
	last_name varchar(255) NULL,
    relationship VARCHAR(50)
);
-- Index for faster queries
CREATE INDEX idx_employees_emergency_contacts_employee_id ON employees_emergency_contacts(employee_id);

CREATE TABLE IF NOT EXISTS employees_emergency_contact_phone_numbers (
    emergency_contact_info_id SERIAL PRIMARY KEY,
    emergency_contact_id INTEGER NOT NULL REFERENCES employees_emergency_contacts(emergency_contact_id),
    phone_number_id INTEGER NOT NULL REFERENCES phone_numbers(phone_number_id)
);
-- Index for faster queries
CREATE INDEX idx_employees_emergency_contact_phone_numbers_emergency_contact_id ON employees_emergency_contact_phone_numbers(emergency_contact_id);
CREATE INDEX idx_employees_emergency_contact_phone_numbers_phone_number_id ON employees_emergency_contact_phone_numbers(phone_number_id);

CREATE TABLE IF NOT EXISTS employees_emergency_contact_emails (
    emergency_contact_email_id SERIAL PRIMARY KEY,
    emergency_contact_id INTEGER NOT NULL REFERENCES employees_emergency_contacts(emergency_contact_id),
    email_address_id INTEGER NOT NULL REFERENCES email_addresses(email_address_id)
);
-- Index for faster queries
CREATE INDEX idx_employees_emergency_contact_emails_emergency_contact_id ON employees_emergency_contact_emails(emergency_contact_id);
CREATE INDEX idx_employees_emergency_contact_emails_email_address_id ON employees_emergency_contact_emails(email_address_id);

CREATE TABLE IF NOT EXISTS employees_roles (
    employee_role_id SERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL,
    description TEXT
);
-- Index for faster queries
CREATE INDEX idx_employees_roles_role ON employees_roles(role);

CREATE TABLE IF NOT EXISTS employees_assigned_roles_join (
    employee_role_id INTEGER NOT NULL REFERENCES employees_roles(employee_role_id),
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id)
);
CREATE INDEX idx_employees_assigned_roles_employee_id ON employees_assigned_roles_join(employee_id);


CREATE TABLE IF NOT EXISTS permissioins (
    permissioins_id SERIAL PRIMARY KEY,
    permission_tag INTEGER NOT NULL,
    permission_name VARCHAR(50) NOT NULL,
    description TEXT
);
-- Index for faster queries
CREATE INDEX idx_permissioins_permission_tag ON permissioins(permission_tag);
CREATE INDEX idx_permissioins_permission_name ON permissioins(permission_name);
CREATE INDEX idx_permissioins_permission_tag_name ON permissioins(permission_tag, permission_name);

CREATE TABLE IF NOT EXISTS role_permissions (
    role_permission_id SERIAL PRIMARY KEY,
    employee_role_id VARCHAR(50) NOT NULL,
    permissioins_id INTEGER NOT NULL REFERENCES permissioins(permissioins_id)
);
-- Index for faster queries
CREATE INDEX idx_role_permissions_employee_role_id ON role_permissions(employee_role_id);
CREATE INDEX idx_role_permissions_permissioins_id ON role_permissions(permissioins_id);
CREATE INDEX idx_role_permissions_employee_role_permission ON role_permissions(employee_role_id, permissioins_id);


CREATE TABLE IF NOT EXISTS projects (
    project_id SERIAL PRIMARY KEY,
    project VARCHAR(50) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    status int2 NOT NULL
);
-- Index for faster queries
CREATE INDEX idx_projects_project ON projects(project);
CREATE INDEX idx_projects_status ON projects(status);


CREATE TABLE IF NOT EXISTS departments (
    department_id SERIAL PRIMARY KEY,
    department VARCHAR(50) NOT NULL,
    description TEXT,
    status int2 NOT NULL
);        
-- Index for faster queries
CREATE INDEX idx_departments_department ON departments(department);
CREATE INDEX idx_departments_status ON departments(status);

CREATE TABLE IF NOT EXISTS department_location_join (
	address_id  INTEGER NOT NULL REFERENCES addresses(address_id),
    department_id INTEGER NOT NULL REFERENCES departments(department_id)
);
CREATE INDEX idx_department_location_join_department_id ON department_location_join(department_id);
CREATE INDEX idx_department_location_join_address_id ON department_location_join(address_id);

CREATE TABLE IF NOT EXISTS organization (
    organization_id SERIAL PRIMARY KEY,
    parent_organization_id INTEGER REFERENCES organization(organization_id),
    organization VARCHAR(50) NOT NULL,
    description TEXT,
    status int2 NOT NULL
);
-- Index for faster queries
CREATE INDEX idx_organization_organization ON organization(organization);
CREATE INDEX idx_organization_status ON organization(status);
CREATE INDEX idx_organization_parent_organization_id ON organization(parent_organization_id);


CREATE TABLE IF NOT EXISTS employee_department_assignment_join (
    department_id INTEGER NOT NULL REFERENCES departments(department_id),
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id)
);
-- Index for faster queries
CREATE INDEX idx_employee_department_assignment_join_department_id ON employee_department_assignment_join(department_id);
CREATE INDEX idx_employee_department_assignment_join_employee_id ON employee_department_assignment_join(employee_id);

CREATE TABLE IF NOT EXISTS employee_project_assignment_join (
    project_id INTEGER NOT NULL REFERENCES projects(project_id),
    employee_id INTEGER NOT NULL REFERENCES employees(employee_id)
);
-- Index for faster queries
CREATE INDEX idx_employee_project_assignment_join_project_id ON employee_project_assignment_join(project_id);
CREATE INDEX idx_employee_project_assignment_join_employee_id ON employee_project_assignment_join(employee_id);

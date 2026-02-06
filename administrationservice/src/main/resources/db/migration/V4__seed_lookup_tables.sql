-- ============================================================================
-- Seed data for ABAC lookup tables
-- ============================================================================

-- ============================================================================
-- RESOURCE TYPES
-- ============================================================================
INSERT INTO resource_types (type_name, description) VALUES
    ('page', 'Web application page or view'),
    ('api', 'REST API endpoint'),
    ('document', 'Document or file resource'),
    ('database', 'Database table or schema'),
    ('report', 'Report or dashboard'),
    ('service', 'Backend service or microservice'),
    ('module', 'Application module or feature area');

-- ============================================================================
-- ATTRIBUTE CATEGORIES
-- ============================================================================
INSERT INTO attribute_categories (category_name, description) VALUES
    ('subject', 'Attributes describing the user or entity requesting access'),
    ('resource', 'Attributes describing the resource being accessed'),
    ('environment', 'Contextual attributes such as time, location, or IP address'),
    ('action', 'Attributes describing the action being performed');

-- ============================================================================
-- POLICY TYPES
-- ============================================================================
INSERT INTO policy_types (type_name, description) VALUES
    ('permit', 'Policy that grants access when conditions are met'),
    ('deny', 'Policy that denies access when conditions are met');

-- ============================================================================
-- OBLIGATION TYPES
-- ============================================================================
INSERT INTO obligation_types (type_name, description) VALUES
    ('log_access', 'Log the access attempt for auditing purposes'),
    ('notify_admin', 'Send a notification to an administrator'),
    ('encrypt_data', 'Ensure data is encrypted before transmission'),
    ('require_mfa', 'Require multi-factor authentication before proceeding'),
    ('time_limit', 'Restrict access to a specific time window');

-- ============================================================================
-- DATA TYPES
-- ============================================================================
INSERT INTO data_types (type_name, description) VALUES
    ('string', 'Text or string value'),
    ('number', 'Numeric value (integer or decimal)'),
    ('boolean', 'True or false value'),
    ('datetime', 'Date and time value'),
    ('list', 'Comma-separated list of values');

-- ============================================================================
-- OPERATIONS
-- ============================================================================
INSERT INTO operations (operation_name, description) VALUES
    ('read', 'View or read a resource'),
    ('create', 'Create a new resource'),
    ('update', 'Modify an existing resource'),
    ('delete', 'Remove a resource'),
    ('execute', 'Execute or run a resource'),
    ('export', 'Export or download a resource'),
    ('approve', 'Approve a pending resource or request');

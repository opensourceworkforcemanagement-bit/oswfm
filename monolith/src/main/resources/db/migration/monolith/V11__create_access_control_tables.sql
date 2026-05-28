-- Consolidated access control tables from administrationservice V2-V7

-- ============================================================================
-- V2: Application configuration manager tables
-- ============================================================================

CREATE TABLE modules (
    module_id SERIAL PRIMARY KEY,
    module_code VARCHAR(50) UNIQUE NOT NULL,
    module_name VARCHAR(100) NOT NULL,
    module_description TEXT,
    entity_name VARCHAR(100) NOT NULL,
    entity_name_plural VARCHAR(100) NOT NULL,
    id_field VARCHAR(50) NOT NULL,
    display_field VARCHAR(50) NOT NULL,
    icon VARCHAR(50),
    route_path VARCHAR(100),
    is_active BOOLEAN DEFAULT true,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    updated_by INTEGER
);

CREATE TABLE tabs (
    tab_id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES modules(module_id) ON DELETE CASCADE,
    tab_code VARCHAR(50) NOT NULL,
    tab_label VARCHAR(100) NOT NULL,
    tab_description TEXT,
    is_visible BOOLEAN DEFAULT true,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id, tab_code)
);

CREATE TABLE fields (
    field_id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES modules(module_id) ON DELETE CASCADE,
    field_code VARCHAR(50) NOT NULL,
    field_label VARCHAR(100) NOT NULL,
    field_hint TEXT,
    field_type VARCHAR(50) NOT NULL DEFAULT 'text',
    is_enabled BOOLEAN DEFAULT true,
    is_readonly BOOLEAN DEFAULT false,
    is_visible BOOLEAN DEFAULT true,
    is_required BOOLEAN DEFAULT false,
    max_length INTEGER,
    min_value NUMERIC,
    max_value NUMERIC,
    default_value TEXT,
    validation_regex VARCHAR(500),
    validation_message VARCHAR(200),
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id, field_code)
);

CREATE TABLE tab_fields (
    tab_field_id SERIAL PRIMARY KEY,
    tab_id INTEGER NOT NULL REFERENCES tabs(tab_id) ON DELETE CASCADE,
    field_id INTEGER NOT NULL REFERENCES fields(field_id) ON DELETE CASCADE,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(tab_id, field_id)
);

CREATE TABLE field_options (
    option_id SERIAL PRIMARY KEY,
    field_id INTEGER NOT NULL REFERENCES fields(field_id) ON DELETE CASCADE,
    option_value VARCHAR(100) NOT NULL,
    option_label VARCHAR(100) NOT NULL,
    option_description TEXT,
    is_enabled BOOLEAN DEFAULT true,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE columns (
    column_id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES modules(module_id) ON DELETE CASCADE,
    column_code VARCHAR(50) NOT NULL,
    column_label VARCHAR(100) NOT NULL,
    is_visible BOOLEAN DEFAULT true,
    column_width INTEGER,
    formatter_type VARCHAR(50),
    formatter_config JSONB,
    is_sortable BOOLEAN DEFAULT true,
    is_filterable BOOLEAN DEFAULT true,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id, column_code)
);

CREATE TABLE actions (
    action_id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES modules(module_id) ON DELETE CASCADE,
    action_code VARCHAR(50) NOT NULL,
    action_label VARCHAR(100) NOT NULL,
    action_type VARCHAR(50) NOT NULL,
    is_enabled BOOLEAN DEFAULT true,
    is_visible BOOLEAN DEFAULT true,
    icon VARCHAR(50),
    confirmation_message TEXT,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id, action_code)
);

CREATE TABLE pagination_config (
    pagination_id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES modules(module_id) ON DELETE CASCADE,
    is_enabled BOOLEAN DEFAULT true,
    default_page_size INTEGER DEFAULT 10,
    page_size_options INTEGER[] DEFAULT ARRAY[10, 25, 50, 100],
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id)
);

CREATE TABLE filters (
    filter_id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES modules(module_id) ON DELETE CASCADE,
    filter_code VARCHAR(50) NOT NULL,
    filter_label VARCHAR(100) NOT NULL,
    filter_type VARCHAR(50) NOT NULL DEFAULT 'text',
    is_enabled BOOLEAN DEFAULT true,
    is_visible BOOLEAN DEFAULT true,
    default_value TEXT,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id, filter_code)
);

CREATE TABLE filter_options (
    filter_option_id SERIAL PRIMARY KEY,
    filter_id INTEGER NOT NULL REFERENCES filters(filter_id) ON DELETE CASCADE,
    option_value VARCHAR(100) NOT NULL,
    option_label VARCHAR(100) NOT NULL,
    is_enabled BOOLEAN DEFAULT true,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE validation_rules (
    rule_id SERIAL PRIMARY KEY,
    field_id INTEGER NOT NULL REFERENCES fields(field_id) ON DELETE CASCADE,
    rule_type VARCHAR(50) NOT NULL,
    rule_value TEXT,
    error_message VARCHAR(200) NOT NULL,
    is_enabled BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE themes (
    theme_id SERIAL PRIMARY KEY,
    theme_code VARCHAR(50) UNIQUE NOT NULL,
    theme_name VARCHAR(100) NOT NULL,
    theme_description TEXT,
    is_active BOOLEAN DEFAULT true,
    css_variables JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE module_themes (
    module_theme_id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES modules(module_id) ON DELETE CASCADE,
    theme_id INTEGER NOT NULL REFERENCES themes(theme_id) ON DELETE CASCADE,
    is_default BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id, theme_id)
);

CREATE TABLE user_preferences (
    preference_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    module_id INTEGER REFERENCES modules(module_id) ON DELETE CASCADE,
    theme_id INTEGER REFERENCES themes(theme_id) ON DELETE SET NULL,
    page_size INTEGER,
    preferences_json JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, module_id)
);

-- Indexes
CREATE INDEX idx_modules_code ON modules(module_code);
CREATE INDEX idx_modules_active ON modules(is_active);
CREATE INDEX idx_tabs_module ON tabs(module_id);
CREATE INDEX idx_tabs_visible ON tabs(is_visible);
CREATE INDEX idx_fields_module ON fields(module_id);
CREATE INDEX idx_fields_visible ON fields(is_visible);
CREATE INDEX idx_fields_type ON fields(field_type);
CREATE INDEX idx_tab_fields_tab ON tab_fields(tab_id);
CREATE INDEX idx_tab_fields_field ON tab_fields(field_id);
CREATE INDEX idx_field_options_field ON field_options(field_id);
CREATE INDEX idx_columns_module ON columns(module_id);
CREATE INDEX idx_columns_visible ON columns(is_visible);
CREATE INDEX idx_actions_module ON actions(module_id);
CREATE INDEX idx_actions_visible ON actions(is_visible);
CREATE INDEX idx_filters_module ON filters(module_id);
CREATE INDEX idx_filters_visible ON filters(is_visible);
CREATE INDEX idx_user_prefs_user ON user_preferences(user_id);
CREATE INDEX idx_user_prefs_module ON user_preferences(module_id);

-- Triggers for updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_modules_updated_at BEFORE UPDATE ON modules FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_tabs_updated_at BEFORE UPDATE ON tabs FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_fields_updated_at BEFORE UPDATE ON fields FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_field_options_updated_at BEFORE UPDATE ON field_options FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_columns_updated_at BEFORE UPDATE ON columns FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_actions_updated_at BEFORE UPDATE ON actions FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_pagination_config_updated_at BEFORE UPDATE ON pagination_config FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_filters_updated_at BEFORE UPDATE ON filters FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_validation_rules_updated_at BEFORE UPDATE ON validation_rules FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_themes_updated_at BEFORE UPDATE ON themes FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_user_preferences_updated_at BEFORE UPDATE ON user_preferences FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Sample data: Work Code Management module
INSERT INTO modules (module_code, module_name, module_description, entity_name, entity_name_plural, id_field, display_field, icon, route_path)
VALUES ('work_code_management', 'Work Code Management', 'Manage work codes and their configurations', 'Work Code', 'Work Codes', 'work_code_id', 'short_work_code', 'Activity', '/workcodes');

DO $$
DECLARE
    v_module_id INTEGER;
    v_tab_general_id INTEGER;
    v_tab_details_id INTEGER;
    v_field_prefix_id INTEGER;
    v_field_suffix_id INTEGER;
    v_field_short_code_id INTEGER;
    v_field_long_code_id INTEGER;
    v_field_status_id INTEGER;
    v_field_description_id INTEGER;
BEGIN
    SELECT module_id INTO v_module_id FROM modules WHERE module_code = 'work_code_management';
    INSERT INTO tabs (module_id, tab_code, tab_label, display_order) VALUES (v_module_id, 'general', 'General Information', 1) RETURNING tab_id INTO v_tab_general_id;
    INSERT INTO tabs (module_id, tab_code, tab_label, display_order) VALUES (v_module_id, 'details', 'Details', 2) RETURNING tab_id INTO v_tab_details_id;
    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order) VALUES (v_module_id, 'prefix', 'Prefix', 'Maximum 10 characters', 'text', false, 10, 1) RETURNING field_id INTO v_field_prefix_id;
    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order) VALUES (v_module_id, 'suffix', 'Suffix', 'Maximum 10 characters', 'text', false, 10, 2) RETURNING field_id INTO v_field_suffix_id;
    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order) VALUES (v_module_id, 'short_work_code', 'Short Work Code', 'Required, maximum 10 characters', 'text', true, 10, 3) RETURNING field_id INTO v_field_short_code_id;
    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order) VALUES (v_module_id, 'long_work_code', 'Long Work Code', 'Required, maximum 50 characters', 'text', true, 50, 4) RETURNING field_id INTO v_field_long_code_id;
    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, display_order) VALUES (v_module_id, 'status', 'Status', '', 'select', false, 5) RETURNING field_id INTO v_field_status_id;
    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order) VALUES (v_module_id, 'description', 'Description', 'Optional detailed description', 'textarea', false, 1000, 6) RETURNING field_id INTO v_field_description_id;
    INSERT INTO tab_fields (tab_id, field_id, display_order) VALUES (v_tab_general_id, v_field_prefix_id, 1), (v_tab_general_id, v_field_suffix_id, 2), (v_tab_general_id, v_field_short_code_id, 3), (v_tab_general_id, v_field_long_code_id, 4), (v_tab_general_id, v_field_status_id, 5), (v_tab_details_id, v_field_description_id, 1);
    INSERT INTO field_options (field_id, option_value, option_label, display_order) VALUES (v_field_status_id, '0', 'Inactive', 1), (v_field_status_id, '1', 'Active', 2), (v_field_status_id, '2', 'Pending', 3), (v_field_status_id, '3', 'Archived', 4);
    INSERT INTO columns (module_id, column_code, column_label, is_visible, display_order) VALUES (v_module_id, 'work_code_id', 'ID', true, 1), (v_module_id, 'prefix', 'Prefix', true, 2), (v_module_id, 'suffix', 'Suffix', true, 3), (v_module_id, 'short_work_code', 'Short Code', true, 4), (v_module_id, 'long_work_code', 'Long Code', true, 5), (v_module_id, 'description', 'Description', true, 6), (v_module_id, 'status', 'Status', true, 7);
    INSERT INTO actions (module_id, action_code, action_label, action_type, display_order) VALUES (v_module_id, 'add', 'Add Work Code', 'add', 1), (v_module_id, 'edit', 'Edit', 'edit', 2), (v_module_id, 'delete', 'Delete', 'delete', 3), (v_module_id, 'save', 'Save', 'save', 4), (v_module_id, 'cancel', 'Cancel', 'cancel', 5);
    INSERT INTO pagination_config (module_id, is_enabled, default_page_size, page_size_options) VALUES (v_module_id, true, 10, ARRAY[10, 25, 50, 100]);
END $$;

INSERT INTO themes (theme_code, theme_name, theme_description, css_variables) VALUES
    ('light', 'Light Theme', 'Default light theme', '{"--color-primary": "#3b82f6", "--color-background": "#ffffff"}'::jsonb),
    ('dark', 'Dark Theme', 'Dark mode theme', '{"--color-primary": "#3b82f6", "--color-background": "#1e293b"}'::jsonb),
    ('brand-a', 'Brand A Theme', 'Teal branded theme', '{"--color-primary": "#14b8a6", "--color-background": "#f0fdfa"}'::jsonb);

CREATE VIEW v_module_config AS
SELECT
    m.module_id, m.module_code, m.module_name, m.entity_name, m.entity_name_plural, m.id_field, m.display_field,
    (SELECT json_agg(jsonb_build_object('tab_id', t.tab_id, 'tab_code', t.tab_code, 'tab_label', t.tab_label, 'is_visible', t.is_visible, 'display_order', t.display_order) ORDER BY t.display_order) FROM tabs t WHERE t.module_id = m.module_id) AS tabs,
    (SELECT json_agg(jsonb_build_object('field_id', f.field_id, 'field_code', f.field_code, 'field_label', f.field_label, 'field_type', f.field_type, 'is_required', f.is_required, 'is_visible', f.is_visible) ORDER BY f.display_order) FROM fields f WHERE f.module_id = m.module_id) AS fields,
    (SELECT json_agg(jsonb_build_object('column_id', c.column_id, 'column_code', c.column_code, 'column_label', c.column_label, 'is_visible', c.is_visible) ORDER BY c.display_order) FROM columns c WHERE c.module_id = m.module_id) AS columns,
    (SELECT json_agg(jsonb_build_object('action_id', a.action_id, 'action_code', a.action_code, 'action_label', a.action_label, 'is_enabled', a.is_enabled, 'is_visible', a.is_visible) ORDER BY a.display_order) FROM actions a WHERE a.module_id = m.module_id) AS actions
FROM modules m WHERE m.is_active = true;

CREATE OR REPLACE FUNCTION get_module_config(p_module_code VARCHAR)
RETURNS JSON AS $$
DECLARE v_config JSON;
BEGIN
    SELECT row_to_json(v_module_config) INTO v_config FROM v_module_config WHERE module_code = p_module_code;
    RETURN v_config;
END;
$$ LANGUAGE plpgsql;

-- ============================================================================
-- V3: ABAC (Attribute-Based Access Control) tables
-- ============================================================================

CREATE TABLE resource_types (
    resource_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE attribute_categories (
    attribute_category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE policy_types (
    policy_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE obligation_types (
    obligation_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE data_types (
    data_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE resources (
    resource_id SERIAL PRIMARY KEY,
    resource_type_id INTEGER NOT NULL REFERENCES resource_types(resource_type_id),
    resource_name VARCHAR(255) NOT NULL,
    resource_uri VARCHAR(500),
    owner_id INTEGER REFERENCES oswfm_users(user_id) ON DELETE SET NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE resources_audit_log (
    resources_audit_log_id SERIAL PRIMARY KEY,
    resource_id INTEGER NOT NULL REFERENCES resources(resource_id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    operation_type_id INTEGER NULL
);

CREATE INDEX idx_resources_audit_log_resource_id ON resources_audit_log(resource_id);
CREATE INDEX idx_resources_audit_log_create_at ON resources_audit_log(created_at);

CREATE TABLE resource_hierarchies (
    hierarchy_id SERIAL PRIMARY KEY,
    parent_resource_id INTEGER NOT NULL REFERENCES resources(resource_id) ON DELETE CASCADE,
    child_resource_id INTEGER NOT NULL REFERENCES resources(resource_id) ON DELETE CASCADE,
    UNIQUE(parent_resource_id, child_resource_id),
    CHECK (parent_resource_id != child_resource_id)
);

CREATE TABLE attribute_definitions (
    attribute_id SERIAL PRIMARY KEY,
    attribute_name VARCHAR(255) UNIQUE NOT NULL,
    attribute_category_id INTEGER NOT NULL REFERENCES attribute_categories(attribute_category_id),
    data_type_id INTEGER NOT NULL REFERENCES data_types(data_type_id),
    description TEXT,
    is_required BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subject_attributes (
    subject_attr_id SERIAL PRIMARY KEY,
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    attribute_value TEXT NOT NULL,
    valid_from TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    valid_until TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(attribute_id, attribute_value, valid_from)
);

CREATE TABLE resource_attributes (
    resource_attr_id SERIAL PRIMARY KEY,
    resource_id INTEGER NOT NULL REFERENCES resources(resource_id) ON DELETE CASCADE,
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    attribute_value TEXT NOT NULL,
    valid_from TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    valid_until TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(resource_id, attribute_id, valid_from)
);

CREATE TABLE environment_attributes (
    env_attr_id SERIAL PRIMARY KEY,
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    attribute_value TEXT NOT NULL,
    context_identifier VARCHAR(255),
    captured_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Operations (renamed from "actions" to avoid conflict with V2 actions table)
CREATE TABLE operations (
    operation_id SERIAL PRIMARY KEY,
    operation_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_attributes (
    operation_attr_id SERIAL PRIMARY KEY,
    operation_id INTEGER NOT NULL REFERENCES operations(operation_id) ON DELETE CASCADE,
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    attribute_value TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(operation_id, attribute_id)
);

CREATE TABLE policies (
    policy_id SERIAL PRIMARY KEY,
    policy_name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    policy_type_id INTEGER NOT NULL REFERENCES policy_types(policy_type_id),
    priority INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_by INTEGER REFERENCES oswfm_users(user_id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE policy_rules (
    rule_id SERIAL PRIMARY KEY,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    rule_name VARCHAR(255),
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    operator VARCHAR(50) NOT NULL,
    comparison_value TEXT NOT NULL,
    logical_operator VARCHAR(10) DEFAULT 'AND',
    rule_order INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE policy_resource_targets (
    target_id SERIAL PRIMARY KEY,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    resource_id INTEGER REFERENCES resources(resource_id) ON DELETE CASCADE,
    resource_type_id INTEGER REFERENCES resource_types(resource_type_id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CHECK ((resource_id IS NOT NULL) OR (resource_type_id IS NOT NULL))
);

CREATE TABLE policy_operation_targets (
    target_id SERIAL PRIMARY KEY,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    operation_id INTEGER NOT NULL REFERENCES operations(operation_id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(policy_id, operation_id)
);

CREATE TABLE access_requests (
    request_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    resource_id INTEGER NOT NULL REFERENCES resources(resource_id) ON DELETE CASCADE,
    operation_id INTEGER NOT NULL REFERENCES operations(operation_id) ON DELETE CASCADE,
    decision VARCHAR(20) NOT NULL,
    decision_reason TEXT,
    applied_policy_id INTEGER REFERENCES policies(policy_id) ON DELETE SET NULL,
    request_timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    source_ip INET,
    user_agent TEXT,
    session_id VARCHAR(255)
);

CREATE TABLE policy_evaluation_audit (
    audit_id SERIAL PRIMARY KEY,
    request_id INTEGER NOT NULL REFERENCES access_requests(request_id) ON DELETE CASCADE,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    evaluation_result VARCHAR(20) NOT NULL,
    evaluation_details JSONB,
    evaluated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE policy_obligations (
    obligation_id SERIAL PRIMARY KEY,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    obligation_type_id INTEGER NOT NULL REFERENCES obligation_types(obligation_type_id),
    obligation_params JSONB,
    is_mandatory BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_subject_attributes (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    subject_attr_id INTEGER NOT NULL REFERENCES subject_attributes(subject_attr_id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, subject_attr_id)
);

CREATE TABLE group_subject_attributes (
    id SERIAL PRIMARY KEY,
    group_id INTEGER NOT NULL REFERENCES user_groups(group_id) ON DELETE CASCADE,
    subject_attr_id INTEGER NOT NULL REFERENCES subject_attributes(subject_attr_id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(group_id, subject_attr_id)
);

-- Indexes
CREATE INDEX idx_resources_type ON resources(resource_type_id);
CREATE INDEX idx_resources_owner ON resources(owner_id);
CREATE INDEX idx_resources_is_active ON resources(is_active);
CREATE INDEX idx_resource_hierarchies_parent ON resource_hierarchies(parent_resource_id);
CREATE INDEX idx_resource_hierarchies_child ON resource_hierarchies(child_resource_id);
CREATE INDEX idx_attribute_definitions_category ON attribute_definitions(attribute_category_id);
CREATE INDEX idx_subject_attributes_attribute ON subject_attributes(attribute_id);
CREATE INDEX idx_subject_attributes_valid ON subject_attributes(valid_from, valid_until);
CREATE INDEX idx_resource_attributes_resource ON resource_attributes(resource_id);
CREATE INDEX idx_resource_attributes_attribute ON resource_attributes(attribute_id);
CREATE INDEX idx_resource_attributes_valid ON resource_attributes(valid_from, valid_until);
CREATE INDEX idx_environment_attributes_context ON environment_attributes(context_identifier);
CREATE INDEX idx_user_subject_attributes_user ON user_subject_attributes(user_id);
CREATE INDEX idx_user_subject_attributes_attr ON user_subject_attributes(subject_attr_id);
CREATE INDEX idx_group_subject_attributes_group ON group_subject_attributes(group_id);
CREATE INDEX idx_group_subject_attributes_attr ON group_subject_attributes(subject_attr_id);
CREATE INDEX idx_policies_is_active ON policies(is_active);
CREATE INDEX idx_policies_priority ON policies(priority DESC);
CREATE INDEX idx_policy_rules_policy ON policy_rules(policy_id);
CREATE INDEX idx_policy_rules_attribute ON policy_rules(attribute_id);
CREATE INDEX idx_policy_resource_targets_policy ON policy_resource_targets(policy_id);
CREATE INDEX idx_policy_resource_targets_resource ON policy_resource_targets(resource_id);
CREATE INDEX idx_policy_operation_targets_policy ON policy_operation_targets(policy_id);
CREATE INDEX idx_policy_operation_targets_operation ON policy_operation_targets(operation_id);
CREATE INDEX idx_access_requests_user ON access_requests(user_id);
CREATE INDEX idx_access_requests_resource ON access_requests(resource_id);
CREATE INDEX idx_access_requests_operation ON access_requests(operation_id);
CREATE INDEX idx_access_requests_timestamp ON access_requests(request_timestamp);
CREATE INDEX idx_access_requests_decision ON access_requests(decision);
CREATE INDEX idx_policy_evaluation_audit_request ON policy_evaluation_audit(request_id);
CREATE INDEX idx_policy_evaluation_audit_policy ON policy_evaluation_audit(policy_id);

-- ============================================================================
-- V4: Seed ABAC lookup tables
-- ============================================================================

INSERT INTO resource_types (type_name, description) VALUES
    ('page', 'Web application page or view'),
    ('api', 'REST API endpoint'),
    ('document', 'Document or file resource'),
    ('database', 'Database table or schema'),
    ('report', 'Report or dashboard'),
    ('service', 'Backend service or microservice'),
    ('module', 'Application module or feature area');

INSERT INTO attribute_categories (category_name, description) VALUES
    ('subject', 'Attributes describing the user or entity requesting access'),
    ('resource', 'Attributes describing the resource being accessed'),
    ('environment', 'Contextual attributes such as time, location, or IP address'),
    ('action', 'Attributes describing the action being performed');

INSERT INTO policy_types (type_name, description) VALUES
    ('permit', 'Policy that grants access when conditions are met'),
    ('deny', 'Policy that denies access when conditions are met');

INSERT INTO obligation_types (type_name, description) VALUES
    ('log_access', 'Log the access attempt for auditing purposes'),
    ('notify_admin', 'Send a notification to an administrator'),
    ('encrypt_data', 'Ensure data is encrypted before transmission'),
    ('require_mfa', 'Require multi-factor authentication before proceeding'),
    ('time_limit', 'Restrict access to a specific time window');

INSERT INTO data_types (type_name, description) VALUES
    ('string', 'Text or string value'),
    ('number', 'Numeric value (integer or decimal)'),
    ('boolean', 'True or false value'),
    ('datetime', 'Date and time value'),
    ('list', 'Comma-separated list of values');

INSERT INTO operations (operation_name, description) VALUES
    ('read', 'View or read a resource'),
    ('create', 'Create a new resource'),
    ('update', 'Modify an existing resource'),
    ('delete', 'Remove a resource'),
    ('execute', 'Execute or run a resource'),
    ('export', 'Export or download a resource'),
    ('approve', 'Approve a pending resource or request');

-- ============================================================================
-- V5: Role attribute and role seed values
-- ============================================================================

INSERT INTO attribute_definitions (attribute_name, attribute_category_id, data_type_id, description, is_required)
VALUES ('role', 1, 1, 'The organizational role assigned to the user', FALSE);

INSERT INTO subject_attributes (attribute_id, attribute_value)
SELECT ad.attribute_id, role_value
FROM attribute_definitions ad
CROSS JOIN (VALUES
    ('HR Admin'),
    ('Employee'),
    ('Emergency Contact Management (ECM) Administrator'),
    ('Telework Managing Officer'),
    ('Project Manager'),
    ('Timekeeper'),
    ('Telework Coordinator'),
    ('Supervisor')
) AS roles(role_value)
WHERE ad.attribute_name = 'role';

-- ============================================================================
-- V6: User Management page resources
-- ============================================================================

INSERT INTO resource_types (type_name, description) VALUES
    ('tab', 'UI tab or section within a page'),
    ('field', 'Input field or form control'),
    ('button', 'Action button or control');

INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, 'User Management Page', '/usermanagement/users', TRUE
FROM resource_types rt WHERE rt.type_name = 'page';

INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, vals.rname, vals.ruri, TRUE
FROM resource_types rt
CROSS JOIN (VALUES
    ('User Management - User Tab',     '/usermanagement/users/tab/user'),
    ('User Management - Roles Tab',    '/usermanagement/users/tab/roles'),
    ('User Management - Settings Tab', '/usermanagement/users/tab/settings'),
    ('User Management - Groups Tab',   '/usermanagement/users/tab/groups')
) AS vals(rname, ruri)
WHERE rt.type_name = 'tab';

INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, vals.rname, vals.ruri, TRUE
FROM resource_types rt
CROSS JOIN (VALUES
    ('User Management - Username Field',     '/usermanagement/users/field/userName'),
    ('User Management - First Name Field',   '/usermanagement/users/field/firstName'),
    ('User Management - Middle Name Field',  '/usermanagement/users/field/middleName'),
    ('User Management - Last Name Field',    '/usermanagement/users/field/lastName'),
    ('User Management - Email Field',        '/usermanagement/users/field/email'),
    ('User Management - Password Field',     '/usermanagement/users/field/password'),
    ('User Management - Status Field',       '/usermanagement/users/field/userStatus'),
    ('User Management - Organization Field', '/usermanagement/users/field/organization')
) AS vals(rname, ruri)
WHERE rt.type_name = 'field';

INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, 'User Management - Role Checkbox', '/usermanagement/users/field/roleCheckbox', TRUE
FROM resource_types rt WHERE rt.type_name = 'field';

INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, vals.rname, vals.ruri, TRUE
FROM resource_types rt
CROSS JOIN (VALUES
    ('User Management - Hiring Date Field',  '/usermanagement/users/field/hiringDate'),
    ('User Management - Last Date Field',    '/usermanagement/users/field/lastDate'),
    ('User Management - Worker Type Field',  '/usermanagement/users/field/workerType')
) AS vals(rname, ruri)
WHERE rt.type_name = 'field';

INSERT INTO resources (resource_type_id, resource_name, resource_uri, is_active)
SELECT rt.resource_type_id, vals.rname, vals.ruri, TRUE
FROM resource_types rt
CROSS JOIN (VALUES
    ('User Management - Add User Button',          '/usermanagement/users/button/addUser'),
    ('User Management - Refresh Button',           '/usermanagement/users/button/refresh'),
    ('User Management - Edit Button',              '/usermanagement/users/button/edit'),
    ('User Management - Delete Button',            '/usermanagement/users/button/delete'),
    ('User Management - Save Button',              '/usermanagement/users/button/save'),
    ('User Management - Cancel Button',            '/usermanagement/users/button/cancel'),
    ('User Management - Add To Group Button',      '/usermanagement/users/button/addToGroup'),
    ('User Management - Remove From Group Button', '/usermanagement/users/button/removeFromGroup')
) AS vals(rname, ruri)
WHERE rt.type_name = 'button';

INSERT INTO resource_hierarchies (parent_resource_id, child_resource_id)
SELECT parent.resource_id, child.resource_id
FROM resources parent, resources child
WHERE parent.resource_uri = '/usermanagement/users'
  AND child.resource_uri LIKE '/usermanagement/users/%';

INSERT INTO attribute_definitions (attribute_name, attribute_category_id, data_type_id, description, is_required)
SELECT 'module', 2, 1, 'The application module this resource belongs to', FALSE
WHERE NOT EXISTS (SELECT 1 FROM attribute_definitions WHERE attribute_name = 'module');

INSERT INTO resource_attributes (resource_id, attribute_id, attribute_value)
SELECT r.resource_id, ad.attribute_id, 'usermanagement'
FROM resources r
CROSS JOIN attribute_definitions ad
WHERE r.resource_uri LIKE '/usermanagement/users%'
  AND ad.attribute_name = 'module';

-- ============================================================================
-- V7: ABAC policies for User Management page
-- ============================================================================

-- HR Admin - Full access
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - HR Admin Full Access', 'Grants HR Admin full access to all User Management resources', 1, 100, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is HR Admin', ad.attribute_id, 'equals', 'HR Admin', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - HR Admin Full Access' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - HR Admin Full Access' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - HR Admin Full Access' AND o.operation_name IN ('read', 'create', 'update', 'delete');

-- Supervisor - Read only
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Supervisor Read Access', 'Grants Supervisor read-only access to User Management', 1, 50, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Supervisor', ad.attribute_id, 'equals', 'Supervisor', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Supervisor Read Access' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Supervisor Read Access' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Supervisor Read Access' AND o.operation_name = 'read';
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Supervisor Deny Write', 'Denies Supervisor create/update/delete on User Management', 2, 60, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Supervisor', ad.attribute_id, 'equals', 'Supervisor', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Supervisor Deny Write' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Supervisor Deny Write' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Supervisor Deny Write' AND o.operation_name IN ('create', 'update', 'delete');

-- Employee - Read only
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Employee Read Access', 'Grants Employee read-only access to User Management', 1, 50, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Employee', ad.attribute_id, 'equals', 'Employee', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Employee Read Access' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Employee Read Access' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Employee Read Access' AND o.operation_name = 'read';
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Employee Deny Write', 'Denies Employee create/update/delete on User Management', 2, 60, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Employee', ad.attribute_id, 'equals', 'Employee', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Employee Deny Write' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Employee Deny Write' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Employee Deny Write' AND o.operation_name IN ('create', 'update', 'delete');

-- ECM Administrator - Read only
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - ECM Admin Read Access', 'Grants ECM Administrator read-only access to User Management', 1, 50, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is ECM Admin', ad.attribute_id, 'equals', 'Emergency Contact Management (ECM) Administrator', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - ECM Admin Read Access' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - ECM Admin Read Access' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - ECM Admin Read Access' AND o.operation_name = 'read';
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - ECM Admin Deny Write', 'Denies ECM Administrator create/update/delete on User Management', 2, 60, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is ECM Admin', ad.attribute_id, 'equals', 'Emergency Contact Management (ECM) Administrator', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - ECM Admin Deny Write' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - ECM Admin Deny Write' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - ECM Admin Deny Write' AND o.operation_name IN ('create', 'update', 'delete');

-- Telework Managing Officer - Read only
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Telework MO Read Access', 'Grants Telework Managing Officer read-only access to User Management', 1, 50, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Telework MO', ad.attribute_id, 'equals', 'Telework Managing Officer', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Telework MO Read Access' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Telework MO Read Access' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Telework MO Read Access' AND o.operation_name = 'read';
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Telework MO Deny Write', 'Denies Telework Managing Officer create/update/delete on User Management', 2, 60, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Telework MO', ad.attribute_id, 'equals', 'Telework Managing Officer', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Telework MO Deny Write' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Telework MO Deny Write' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Telework MO Deny Write' AND o.operation_name IN ('create', 'update', 'delete');

-- Project Manager - Read only
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Project Manager Read Access', 'Grants Project Manager read-only access to User Management', 1, 50, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Project Manager', ad.attribute_id, 'equals', 'Project Manager', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Project Manager Read Access' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Project Manager Read Access' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Project Manager Read Access' AND o.operation_name = 'read';
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Project Manager Deny Write', 'Denies Project Manager create/update/delete on User Management', 2, 60, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Project Manager', ad.attribute_id, 'equals', 'Project Manager', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Project Manager Deny Write' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Project Manager Deny Write' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Project Manager Deny Write' AND o.operation_name IN ('create', 'update', 'delete');

-- Timekeeper - Read only
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Timekeeper Read Access', 'Grants Timekeeper read-only access to User Management', 1, 50, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Timekeeper', ad.attribute_id, 'equals', 'Timekeeper', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Timekeeper Read Access' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Timekeeper Read Access' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Timekeeper Read Access' AND o.operation_name = 'read';
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Timekeeper Deny Write', 'Denies Timekeeper create/update/delete on User Management', 2, 60, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Timekeeper', ad.attribute_id, 'equals', 'Timekeeper', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Timekeeper Deny Write' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Timekeeper Deny Write' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Timekeeper Deny Write' AND o.operation_name IN ('create', 'update', 'delete');

-- Telework Coordinator - Read only
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Telework Coord Read Access', 'Grants Telework Coordinator read-only access to User Management', 1, 50, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Telework Coordinator', ad.attribute_id, 'equals', 'Telework Coordinator', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Telework Coord Read Access' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Telework Coord Read Access' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Telework Coord Read Access' AND o.operation_name = 'read';
INSERT INTO policies (policy_name, description, policy_type_id, priority, is_active) VALUES ('UM - Telework Coord Deny Write', 'Denies Telework Coordinator create/update/delete on User Management', 2, 60, TRUE);
INSERT INTO policy_rules (policy_id, rule_name, attribute_id, operator, comparison_value, logical_operator, rule_order) SELECT p.policy_id, 'Role is Telework Coordinator', ad.attribute_id, 'equals', 'Telework Coordinator', 'AND', 1 FROM policies p, attribute_definitions ad WHERE p.policy_name = 'UM - Telework Coord Deny Write' AND ad.attribute_name = 'role';
INSERT INTO policy_resource_targets (policy_id, resource_id) SELECT p.policy_id, r.resource_id FROM policies p, resources r WHERE p.policy_name = 'UM - Telework Coord Deny Write' AND r.resource_uri = '/usermanagement/users';
INSERT INTO policy_operation_targets (policy_id, operation_id) SELECT p.policy_id, o.operation_id FROM policies p, operations o WHERE p.policy_name = 'UM - Telework Coord Deny Write' AND o.operation_name IN ('create', 'update', 'delete');

-- Obligations: log denied write operations
INSERT INTO policy_obligations (policy_id, obligation_type_id, obligation_params, is_mandatory)
SELECT p.policy_id, ot.obligation_type_id,
       ('{"message": "Write operation denied for ' || REPLACE(p.policy_name, 'UM - ', '') || '", "severity": "info"}')::jsonb,
       TRUE
FROM policies p
CROSS JOIN obligation_types ot
WHERE p.policy_name LIKE 'UM - % Deny Write'
  AND ot.type_name = 'log_access';

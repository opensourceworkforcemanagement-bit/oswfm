-- ============================================================================
-- POSTGRESQL SCHEMA FOR PAGE CONFIGURATION
-- Complete database structure to store dynamic page configurations
-- ============================================================================

-- ============================================================================
-- 1. MODULES TABLE
-- Stores the top-level modules/pages in the application
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

COMMENT ON TABLE modules IS 'Stores module/page definitions';
COMMENT ON COLUMN modules.module_code IS 'Unique code for the module (e.g., user_management, work_code_management)';
COMMENT ON COLUMN modules.entity_name IS 'Singular entity name (e.g., User, Work Code)';
COMMENT ON COLUMN modules.entity_name_plural IS 'Plural entity name (e.g., Users, Work Codes)';
COMMENT ON COLUMN modules.id_field IS 'Field name used as the primary identifier';
COMMENT ON COLUMN modules.display_field IS 'Field name used for display purposes';

-- ============================================================================
-- 2. TABS TABLE
-- Stores tab configurations for forms
-- ============================================================================
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

COMMENT ON TABLE tabs IS 'Stores tab configurations for module forms';
COMMENT ON COLUMN tabs.tab_code IS 'Unique code for the tab within the module (e.g., general, details)';

-- ============================================================================
-- 3. FIELDS TABLE
-- Stores field configurations for forms
-- ============================================================================
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

COMMENT ON TABLE fields IS 'Stores field configurations for module forms';
COMMENT ON COLUMN fields.field_type IS 'Field input type: text, textarea, select, number, date, email, password, boolean, file, image, url, tel, color, time, datetime-local, month, checkbox, radio';
COMMENT ON COLUMN fields.validation_regex IS 'Regular expression for custom validation';

-- ============================================================================
-- 4. TAB_FIELDS TABLE
-- Maps fields to tabs (many-to-many relationship)
-- ============================================================================
CREATE TABLE tab_fields (
    tab_field_id SERIAL PRIMARY KEY,
    tab_id INTEGER NOT NULL REFERENCES tabs(tab_id) ON DELETE CASCADE,
    field_id INTEGER NOT NULL REFERENCES fields(field_id) ON DELETE CASCADE,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(tab_id, field_id)
);

COMMENT ON TABLE tab_fields IS 'Maps fields to tabs with display order';

-- ============================================================================
-- 5. FIELD_OPTIONS TABLE
-- Stores dropdown/select options for fields
-- ============================================================================
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

COMMENT ON TABLE field_options IS 'Stores dropdown/select options for fields';

-- ============================================================================
-- 6. COLUMNS TABLE
-- Stores column configurations for list/table views
-- ============================================================================
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

COMMENT ON TABLE columns IS 'Stores column configurations for list/table views';
COMMENT ON COLUMN columns.formatter_type IS 'Type of formatter: currency, date, status, custom';
COMMENT ON COLUMN columns.formatter_config IS 'JSON configuration for the formatter';

-- ============================================================================
-- 7. ACTIONS TABLE
-- Stores action configurations (add, edit, delete, etc.)
-- ============================================================================
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

COMMENT ON TABLE actions IS 'Stores action configurations for modules';
COMMENT ON COLUMN actions.action_type IS 'Type of action: add, edit, delete, save, cancel, custom';
COMMENT ON COLUMN actions.confirmation_message IS 'Confirmation message for destructive actions';

-- ============================================================================
-- 8. PAGINATION_CONFIG TABLE
-- Stores pagination configurations
-- ============================================================================
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

COMMENT ON TABLE pagination_config IS 'Stores pagination configurations for modules';

-- ============================================================================
-- 9. FILTERS TABLE
-- Stores filter configurations for list views
-- ============================================================================
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

COMMENT ON TABLE filters IS 'Stores filter configurations for list views';
COMMENT ON COLUMN filters.filter_type IS 'Filter input type: text, select, date, range';

-- ============================================================================
-- 10. FILTER_OPTIONS TABLE
-- Stores dropdown options for filter fields
-- ============================================================================
CREATE TABLE filter_options (
    filter_option_id SERIAL PRIMARY KEY,
    filter_id INTEGER NOT NULL REFERENCES filters(filter_id) ON DELETE CASCADE,
    option_value VARCHAR(100) NOT NULL,
    option_label VARCHAR(100) NOT NULL,
    is_enabled BOOLEAN DEFAULT true,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE filter_options IS 'Stores dropdown options for filter fields';

-- ============================================================================
-- 11. VALIDATION_RULES TABLE
-- Stores custom validation rules for fields
-- ============================================================================
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

COMMENT ON TABLE validation_rules IS 'Stores custom validation rules for fields';
COMMENT ON COLUMN validation_rules.rule_type IS 'Validation type: min_length, max_length, pattern, custom';

-- ============================================================================
-- 12. THEMES TABLE
-- Stores available themes
-- ============================================================================
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

COMMENT ON TABLE themes IS 'Stores available themes and their CSS variable definitions';

-- ============================================================================
-- 13. MODULE_THEMES TABLE
-- Maps modules to themes (allows per-module theming)
-- ============================================================================
CREATE TABLE module_themes (
    module_theme_id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES modules(module_id) ON DELETE CASCADE,
    theme_id INTEGER NOT NULL REFERENCES themes(theme_id) ON DELETE CASCADE,
    is_default BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id, theme_id)
);

COMMENT ON TABLE module_themes IS 'Maps modules to themes for per-module theming';

-- ============================================================================
-- 14. USER_PREFERENCES TABLE
-- Stores user-specific preferences for modules
-- ============================================================================
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

COMMENT ON TABLE user_preferences IS 'Stores user-specific preferences for modules';

-- ============================================================================
-- INDEXES FOR PERFORMANCE
-- ============================================================================

-- Modules indexes
CREATE INDEX idx_modules_code ON modules(module_code);
CREATE INDEX idx_modules_active ON modules(is_active);

-- Tabs indexes
CREATE INDEX idx_tabs_module ON tabs(module_id);
CREATE INDEX idx_tabs_visible ON tabs(is_visible);

-- Fields indexes
CREATE INDEX idx_fields_module ON fields(module_id);
CREATE INDEX idx_fields_visible ON fields(is_visible);
CREATE INDEX idx_fields_type ON fields(field_type);

-- Tab fields indexes
CREATE INDEX idx_tab_fields_tab ON tab_fields(tab_id);
CREATE INDEX idx_tab_fields_field ON tab_fields(field_id);

-- Field options indexes
CREATE INDEX idx_field_options_field ON field_options(field_id);

-- Columns indexes
CREATE INDEX idx_columns_module ON columns(module_id);
CREATE INDEX idx_columns_visible ON columns(is_visible);

-- Actions indexes
CREATE INDEX idx_actions_module ON actions(module_id);
CREATE INDEX idx_actions_visible ON actions(is_visible);

-- Filters indexes
CREATE INDEX idx_filters_module ON filters(module_id);
CREATE INDEX idx_filters_visible ON filters(is_visible);

-- User preferences indexes
CREATE INDEX idx_user_prefs_user ON user_preferences(user_id);
CREATE INDEX idx_user_prefs_module ON user_preferences(module_id);

-- ============================================================================
-- TRIGGERS FOR UPDATED_AT
-- ============================================================================

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_modules_updated_at BEFORE UPDATE ON modules
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_tabs_updated_at BEFORE UPDATE ON tabs
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_fields_updated_at BEFORE UPDATE ON fields
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_field_options_updated_at BEFORE UPDATE ON field_options
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_columns_updated_at BEFORE UPDATE ON columns
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_actions_updated_at BEFORE UPDATE ON actions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_pagination_config_updated_at BEFORE UPDATE ON pagination_config
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_filters_updated_at BEFORE UPDATE ON filters
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_validation_rules_updated_at BEFORE UPDATE ON validation_rules
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_themes_updated_at BEFORE UPDATE ON themes
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_user_preferences_updated_at BEFORE UPDATE ON user_preferences
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- ============================================================================
-- SAMPLE DATA - WORK CODE MANAGEMENT MODULE
-- ============================================================================

-- Insert Work Code Management module
INSERT INTO modules (module_code, module_name, module_description, entity_name, entity_name_plural, id_field, display_field, icon, route_path)
VALUES ('work_code_management', 'Work Code Management', 'Manage work codes and their configurations', 'Work Code', 'Work Codes', 'work_code_id', 'short_work_code', 'Activity', '/workcodes');

-- Get the module_id
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
    -- Get module ID
    SELECT module_id INTO v_module_id FROM modules WHERE module_code = 'work_code_management';

    -- Insert tabs
    INSERT INTO tabs (module_id, tab_code, tab_label, display_order)
    VALUES (v_module_id, 'general', 'General Information', 1)
    RETURNING tab_id INTO v_tab_general_id;

    INSERT INTO tabs (module_id, tab_code, tab_label, display_order)
    VALUES (v_module_id, 'details', 'Details', 2)
    RETURNING tab_id INTO v_tab_details_id;

    -- Insert fields
    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order)
    VALUES (v_module_id, 'prefix', 'Prefix', 'Maximum 10 characters', 'text', false, 10, 1)
    RETURNING field_id INTO v_field_prefix_id;

    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order)
    VALUES (v_module_id, 'suffix', 'Suffix', 'Maximum 10 characters', 'text', false, 10, 2)
    RETURNING field_id INTO v_field_suffix_id;

    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order)
    VALUES (v_module_id, 'short_work_code', 'Short Work Code', 'Required, maximum 10 characters', 'text', true, 10, 3)
    RETURNING field_id INTO v_field_short_code_id;

    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order)
    VALUES (v_module_id, 'long_work_code', 'Long Work Code', 'Required, maximum 50 characters', 'text', true, 50, 4)
    RETURNING field_id INTO v_field_long_code_id;

    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, display_order)
    VALUES (v_module_id, 'status', 'Status', '', 'select', false, 5)
    RETURNING field_id INTO v_field_status_id;

    INSERT INTO fields (module_id, field_code, field_label, field_hint, field_type, is_required, max_length, display_order)
    VALUES (v_module_id, 'description', 'Description', 'Optional detailed description', 'textarea', false, 1000, 6)
    RETURNING field_id INTO v_field_description_id;

    -- Map fields to tabs
    INSERT INTO tab_fields (tab_id, field_id, display_order)
    VALUES 
        (v_tab_general_id, v_field_prefix_id, 1),
        (v_tab_general_id, v_field_suffix_id, 2),
        (v_tab_general_id, v_field_short_code_id, 3),
        (v_tab_general_id, v_field_long_code_id, 4),
        (v_tab_general_id, v_field_status_id, 5),
        (v_tab_details_id, v_field_description_id, 1);

    -- Insert field options for status
    INSERT INTO field_options (field_id, option_value, option_label, display_order)
    VALUES 
        (v_field_status_id, '0', 'Inactive', 1),
        (v_field_status_id, '1', 'Active', 2),
        (v_field_status_id, '2', 'Pending', 3),
        (v_field_status_id, '3', 'Archived', 4);

    -- Insert columns
    INSERT INTO columns (module_id, column_code, column_label, is_visible, display_order)
    VALUES 
        (v_module_id, 'work_code_id', 'ID', true, 1),
        (v_module_id, 'prefix', 'Prefix', true, 2),
        (v_module_id, 'suffix', 'Suffix', true, 3),
        (v_module_id, 'short_work_code', 'Short Code', true, 4),
        (v_module_id, 'long_work_code', 'Long Code', true, 5),
        (v_module_id, 'description', 'Description', true, 6),
        (v_module_id, 'status', 'Status', true, 7);

    -- Insert actions
    INSERT INTO actions (module_id, action_code, action_label, action_type, display_order)
    VALUES 
        (v_module_id, 'add', 'Add Work Code', 'add', 1),
        (v_module_id, 'edit', 'Edit', 'edit', 2),
        (v_module_id, 'delete', 'Delete', 'delete', 3),
        (v_module_id, 'save', 'Save', 'save', 4),
        (v_module_id, 'cancel', 'Cancel', 'cancel', 5);

    -- Insert pagination config
    INSERT INTO pagination_config (module_id, is_enabled, default_page_size, page_size_options)
    VALUES (v_module_id, true, 10, ARRAY[10, 25, 50, 100]);
END $$;

-- ============================================================================
-- Insert sample themes
-- ============================================================================

INSERT INTO themes (theme_code, theme_name, theme_description, css_variables)
VALUES 
    ('light', 'Light Theme', 'Default light theme', '{"--color-primary": "#3b82f6", "--color-background": "#ffffff"}'::jsonb),
    ('dark', 'Dark Theme', 'Dark mode theme', '{"--color-primary": "#3b82f6", "--color-background": "#1e293b"}'::jsonb),
    ('brand-a', 'Brand A Theme', 'Teal branded theme', '{"--color-primary": "#14b8a6", "--color-background": "#f0fdfa"}'::jsonb);

-- ============================================================================
-- VIEWS FOR EASY QUERYING
-- ============================================================================

-- View to get complete module configuration
CREATE VIEW v_module_config AS
SELECT
    m.module_id,
    m.module_code,
    m.module_name,
    m.entity_name,
    m.entity_name_plural,
    m.id_field,
    m.display_field,
    (SELECT json_agg(jsonb_build_object(
        'tab_id', t.tab_id,
        'tab_code', t.tab_code,
        'tab_label', t.tab_label,
        'is_visible', t.is_visible,
        'display_order', t.display_order
    ) ORDER BY t.display_order)
    FROM tabs t WHERE t.module_id = m.module_id) AS tabs,
    (SELECT json_agg(jsonb_build_object(
        'field_id', f.field_id,
        'field_code', f.field_code,
        'field_label', f.field_label,
        'field_type', f.field_type,
        'is_required', f.is_required,
        'is_visible', f.is_visible
    ) ORDER BY f.display_order)
    FROM fields f WHERE f.module_id = m.module_id) AS fields,
    (SELECT json_agg(jsonb_build_object(
        'column_id', c.column_id,
        'column_code', c.column_code,
        'column_label', c.column_label,
        'is_visible', c.is_visible
    ) ORDER BY c.display_order)
    FROM columns c WHERE c.module_id = m.module_id) AS columns,
    (SELECT json_agg(jsonb_build_object(
        'action_id', a.action_id,
        'action_code', a.action_code,
        'action_label', a.action_label,
        'is_enabled', a.is_enabled,
        'is_visible', a.is_visible
    ) ORDER BY a.display_order)
    FROM actions a WHERE a.module_id = m.module_id) AS actions
FROM modules m
WHERE m.is_active = true;

COMMENT ON VIEW v_module_config IS 'Complete module configuration with all related entities';

-- ============================================================================
-- HELPER FUNCTIONS
-- ============================================================================

-- Function to get module configuration as JSON
CREATE OR REPLACE FUNCTION get_module_config(p_module_code VARCHAR)
RETURNS JSON AS $$
DECLARE
    v_config JSON;
BEGIN
    SELECT row_to_json(v_module_config)
    INTO v_config
    FROM v_module_config
    WHERE module_code = p_module_code;
    
    RETURN v_config;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_module_config IS 'Returns complete module configuration as JSON';

-- Example usage:
-- SELECT get_module_config('work_code_management');
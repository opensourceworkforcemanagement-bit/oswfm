-- ============================================================================
-- Attribute Based Access Control
-- ============================================================================


-- ============================================================================
-- LOOKUP TABLES
-- ============================================================================

-- Resource types - e.g., 'page', 'api', 'document', 'database'
CREATE TABLE resource_types (
    resource_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Attribute categories - e.g., 'subject', 'resource', 'environment', 'action'
CREATE TABLE attribute_categories (
    attribute_category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Policy types - e.g., 'permit', 'deny'
CREATE TABLE policy_types (
    policy_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Obligation types - e.g., 'log_access', 'notify_admin', 'encrypt_data'
CREATE TABLE obligation_types (
    obligation_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Data types - e.g., 'string', 'number', 'boolean', 'datetime', 'list'
CREATE TABLE data_types (
    data_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- RESOURCES AND OBJECTS
-- ============================================================================

-- Resources table - represents objects being protected
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
-- Index for faster queries
CREATE INDEX idx_resources_audit_log_resource_id ON resources_audit_log(resource_id);
CREATE INDEX idx_resources_audit_log_create_at ON resources_audit_log(created_at);

-- Resource hierarchies (for nested resources)
CREATE TABLE resource_hierarchies (
    hierarchy_id SERIAL PRIMARY KEY,
    parent_resource_id INTEGER NOT NULL REFERENCES resources(resource_id) ON DELETE CASCADE,
    child_resource_id INTEGER NOT NULL REFERENCES resources(resource_id) ON DELETE CASCADE,
    UNIQUE(parent_resource_id, child_resource_id),
    CHECK (parent_resource_id != child_resource_id)
);

-- ============================================================================
-- ATTRIBUTES
-- ============================================================================

-- Attribute definitions - defines available attributes in the system
CREATE TABLE attribute_definitions (
    attribute_id SERIAL PRIMARY KEY,
    attribute_name VARCHAR(255) UNIQUE NOT NULL,
    attribute_category_id INTEGER NOT NULL REFERENCES attribute_categories(attribute_category_id),
    data_type_id INTEGER NOT NULL REFERENCES data_types(data_type_id),
    description TEXT,
    is_required BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Subject attributes (user attributes)
CREATE TABLE subject_attributes (
    subject_attr_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    attribute_value TEXT NOT NULL,
    valid_from TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    valid_until TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, attribute_id, valid_from)
);

-- Resource attributes
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

-- Environment attributes (contextual attributes like time, location, IP)
CREATE TABLE environment_attributes (
    env_attr_id SERIAL PRIMARY KEY,
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    attribute_value TEXT NOT NULL,
    context_identifier VARCHAR(255),  -- e.g., session_id, request_id
    captured_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- OPERATIONS (formerly ACTIONS - renamed to avoid conflict with V2 actions table)
-- ============================================================================

-- Operations table - defines possible operations for access control
CREATE TABLE operations (
    operation_id SERIAL PRIMARY KEY,
    operation_name VARCHAR(100) UNIQUE NOT NULL,  -- e.g., 'read', 'write', 'delete', 'execute'
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Operation attributes
CREATE TABLE operation_attributes (
    operation_attr_id SERIAL PRIMARY KEY,
    operation_id INTEGER NOT NULL REFERENCES operations(operation_id) ON DELETE CASCADE,
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    attribute_value TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(operation_id, attribute_id)
);

-- ============================================================================
-- POLICIES
-- ============================================================================

-- Policy table - defines access control policies
CREATE TABLE policies (
    policy_id SERIAL PRIMARY KEY,
    policy_name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    policy_type_id INTEGER NOT NULL REFERENCES policy_types(policy_type_id),
    priority INTEGER DEFAULT 0,  -- Higher priority = evaluated first
    is_active BOOLEAN DEFAULT TRUE,
    created_by INTEGER REFERENCES oswfm_users(user_id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Policy rules - conditions that must be met for policy to apply
CREATE TABLE policy_rules (
    rule_id SERIAL PRIMARY KEY,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    rule_name VARCHAR(255),
    attribute_id INTEGER NOT NULL REFERENCES attribute_definitions(attribute_id) ON DELETE CASCADE,
    operator VARCHAR(50) NOT NULL,  -- 'equals', 'not_equals', 'greater_than', 'less_than', 'contains', 'in', 'not_in'
    comparison_value TEXT NOT NULL,
    logical_operator VARCHAR(10) DEFAULT 'AND',  -- 'AND', 'OR'
    rule_order INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Policy target resources - which resources does this policy apply to
CREATE TABLE policy_resource_targets (
    target_id SERIAL PRIMARY KEY,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    resource_id INTEGER REFERENCES resources(resource_id) ON DELETE CASCADE,
    resource_type_id INTEGER REFERENCES resource_types(resource_type_id),  -- Can specify type instead of specific resource
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CHECK ((resource_id IS NOT NULL) OR (resource_type_id IS NOT NULL))
);

-- Policy target operations - which operations does this policy apply to
CREATE TABLE policy_operation_targets (
    target_id SERIAL PRIMARY KEY,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    operation_id INTEGER NOT NULL REFERENCES operations(operation_id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(policy_id, operation_id)
);

-- ============================================================================
-- ACCESS DECISIONS AND AUDIT
-- ============================================================================

-- Access requests - log of all access attempts
CREATE TABLE access_requests (
    request_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    resource_id INTEGER NOT NULL REFERENCES resources(resource_id) ON DELETE CASCADE,
    operation_id INTEGER NOT NULL REFERENCES operations(operation_id) ON DELETE CASCADE,
    decision VARCHAR(20) NOT NULL,  -- 'PERMIT', 'DENY', 'NOT_APPLICABLE', 'INDETERMINATE'
    decision_reason TEXT,
    applied_policy_id INTEGER REFERENCES policies(policy_id) ON DELETE SET NULL,
    request_timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    source_ip INET,
    user_agent TEXT,
    session_id VARCHAR(255)
);

-- Policy evaluation audit - detailed log of policy evaluation
CREATE TABLE policy_evaluation_audit (
    audit_id SERIAL PRIMARY KEY,
    request_id INTEGER NOT NULL REFERENCES access_requests(request_id) ON DELETE CASCADE,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    evaluation_result VARCHAR(20) NOT NULL,  -- 'MATCH', 'NO_MATCH', 'ERROR'
    evaluation_details JSONB,
    evaluated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Obligations - actions that must be performed when access is granted/denied
CREATE TABLE policy_obligations (
    obligation_id SERIAL PRIMARY KEY,
    policy_id INTEGER NOT NULL REFERENCES policies(policy_id) ON DELETE CASCADE,
    obligation_type_id INTEGER NOT NULL REFERENCES obligation_types(obligation_type_id),
    obligation_params JSONB,
    is_mandatory BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);



-- Resources
CREATE INDEX idx_resources_type ON resources(resource_type_id);
CREATE INDEX idx_resources_owner ON resources(owner_id);
CREATE INDEX idx_resources_is_active ON resources(is_active);
CREATE INDEX idx_resource_hierarchies_parent ON resource_hierarchies(parent_resource_id);
CREATE INDEX idx_resource_hierarchies_child ON resource_hierarchies(child_resource_id);

-- Attributes
CREATE INDEX idx_attribute_definitions_category ON attribute_definitions(attribute_category_id);
CREATE INDEX idx_subject_attributes_user ON subject_attributes(user_id);
CREATE INDEX idx_subject_attributes_attribute ON subject_attributes(attribute_id);
CREATE INDEX idx_subject_attributes_valid ON subject_attributes(valid_from, valid_until);
CREATE INDEX idx_resource_attributes_resource ON resource_attributes(resource_id);
CREATE INDEX idx_resource_attributes_attribute ON resource_attributes(attribute_id);
CREATE INDEX idx_resource_attributes_valid ON resource_attributes(valid_from, valid_until);
CREATE INDEX idx_environment_attributes_context ON environment_attributes(context_identifier);

-- Policies
CREATE INDEX idx_policies_is_active ON policies(is_active);
CREATE INDEX idx_policies_priority ON policies(priority DESC);
CREATE INDEX idx_policy_rules_policy ON policy_rules(policy_id);
CREATE INDEX idx_policy_rules_attribute ON policy_rules(attribute_id);
CREATE INDEX idx_policy_resource_targets_policy ON policy_resource_targets(policy_id);
CREATE INDEX idx_policy_resource_targets_resource ON policy_resource_targets(resource_id);
CREATE INDEX idx_policy_operation_targets_policy ON policy_operation_targets(policy_id);
CREATE INDEX idx_policy_operation_targets_operation ON policy_operation_targets(operation_id);

-- Access requests and audit
CREATE INDEX idx_access_requests_user ON access_requests(user_id);
CREATE INDEX idx_access_requests_resource ON access_requests(resource_id);
CREATE INDEX idx_access_requests_operation ON access_requests(operation_id);
CREATE INDEX idx_access_requests_timestamp ON access_requests(request_timestamp);
CREATE INDEX idx_access_requests_decision ON access_requests(decision);
CREATE INDEX idx_policy_evaluation_audit_request ON policy_evaluation_audit(request_id);
CREATE INDEX idx_policy_evaluation_audit_policy ON policy_evaluation_audit(policy_id);

-- ============================================================================
-- FUNCTIONS AND TRIGGERS
-- ============================================================================

-- Function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



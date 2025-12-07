-- ============================================================================
-- Password Credentials Table
-- Secure password storage following OWASP guidelines
-- ============================================================================

CREATE TABLE IF NOT EXISTS password_credentials(
    credential_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES oswfm_users(user_id) ON DELETE CASCADE,    
    -- Password hash (never store plaintext!)
    password_hash VARCHAR(512) NOT NULL,    
    -- Unique salt per password
    salt VARCHAR(128) NOT NULL,    
    -- Hashing algorithm used
    algorithm VARCHAR(50) NOT NULL DEFAULT 'BCRYPT',
    
    -- Algorithm parameters (e.g., cost factor)
    algorithm_params VARCHAR(255),
    
    -- Account lockout tracking
    failed_attempts INTEGER DEFAULT 0,
    locked_until TIMESTAMP WITH TIME ZONE,

    -- Password lifecycle
    last_changed_at TIMESTAMP WITH TIME ZONE,
    expires_at TIMESTAMP WITH TIME ZONE,
    must_change_password BOOLEAN DEFAULT FALSE,    
    -- Password history (JSON array of previous hashes)
    password_history TEXT,    
    -- Versioning for optimistic locking
    version BIGINT DEFAULT 0
    );


-- Indexes for performance
CREATE INDEX idx_password_creds_user_id ON password_credentials(user_id);
CREATE INDEX idx_password_creds_locked ON password_credentials(locked_until) 
    WHERE locked_until IS NOT NULL;
CREATE INDEX idx_password_creds_expires ON password_credentials(expires_at) 
    WHERE expires_at IS NOT NULL;

-- Trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_password_credential_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_password_credential_update
    BEFORE UPDATE ON password_credentials
    FOR EACH ROW
    EXECUTE FUNCTION update_password_credential_timestamp();


-- ============================================================================
-- Password Reset Tokens (for forgot password flow)
-- ============================================================================

CREATE TABLE IF NOT EXISTS password_reset_tokens (
    token_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    
    -- Secure token
    token_hash VARCHAR(512) NOT NULL,
    
    -- Token expiration
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    
    -- Whether token has been used
    used BOOLEAN DEFAULT FALSE,
    used_at TIMESTAMP WITH TIME ZONE,
    
    -- IP address that requested reset
    request_ip VARCHAR(45),
    
    -- Timestamps
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reset_tokens_user_id ON password_reset_tokens(user_id);
CREATE INDEX idx_reset_tokens_token ON password_reset_tokens(token_hash);
CREATE INDEX idx_reset_tokens_expires ON password_reset_tokens(expires_at);


-- ============================================================================
-- Password Audit Log (track all password-related events)
-- ============================================================================

CREATE TABLE IF NOT EXISTS password_audit_log (
    audit_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    ip_address VARCHAR(45),
    user_agent TEXT,    
	created_at timestamp(6) NULL, -- Considering TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
	created_by INTEGER,
	operation_type_id INTEGER NULL -- CREATED, CHANGED, RESET, VERIFIED_SUCCESS, VERIFIED_FAILED, LOCKED, UNLOCKED
);

CREATE INDEX idx_password_audit_user_id ON password_audit_log(user_id);
CREATE INDEX idx_password_audit_created_at ON password_audit_log(created_at);



-- Password Credentials Table
CREATE TABLE IF NOT EXISTS password_credentials(
    credential_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    password_hash VARCHAR(512) NOT NULL,
    salt VARCHAR(128) NOT NULL,
    algorithm VARCHAR(50) NOT NULL DEFAULT 'BCRYPT',
    algorithm_params VARCHAR(255),
    failed_attempts INTEGER DEFAULT 0,
    locked_until TIMESTAMP WITH TIME ZONE,
    last_changed_at TIMESTAMP WITH TIME ZONE,
    expires_at TIMESTAMP WITH TIME ZONE,
    must_change_password BOOLEAN DEFAULT FALSE,
    password_history TEXT,
    version BIGINT DEFAULT 0
);

CREATE INDEX idx_password_creds_user_id ON password_credentials(user_id);
CREATE INDEX idx_password_creds_locked ON password_credentials(locked_until)
    WHERE locked_until IS NOT NULL;
CREATE INDEX idx_password_creds_expires ON password_credentials(expires_at)
    WHERE expires_at IS NOT NULL;

-- Password Reset Tokens (for forgot password flow)
CREATE TABLE IF NOT EXISTS password_reset_tokens (
    token_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    token_hash VARCHAR(512) NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    used_at TIMESTAMP WITH TIME ZONE,
    request_ip VARCHAR(45),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reset_tokens_user_id ON password_reset_tokens(user_id);
CREATE INDEX idx_reset_tokens_token ON password_reset_tokens(token_hash);
CREATE INDEX idx_reset_tokens_expires ON password_reset_tokens(expires_at);

-- Password Audit Log
CREATE TABLE IF NOT EXISTS password_audit_log (
    audit_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES oswfm_users(user_id) ON DELETE CASCADE,
    ip_address VARCHAR(45),
    user_agent TEXT,
	created_at timestamp(6) NULL,
	created_by INTEGER,
	operation_type_id INTEGER NULL
);

CREATE INDEX idx_password_audit_user_id ON password_audit_log(user_id);
CREATE INDEX idx_password_audit_created_at ON password_audit_log(created_at);

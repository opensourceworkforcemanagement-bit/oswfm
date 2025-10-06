CREATE TABLE ostm_users (
	user_id SERIAL PRIMARY KEY,
	userstatus varchar(255) NULL,
	usertype varchar(255) NULL
);
-- Index for faster queries
CREATE INDEX idx_user_userstatus ON ostm_users(userstatus);
CREATE INDEX idx_user_usertype ON ostm_users(usertype);
CREATE INDEX idx_user_user_id ON ostm_users(user_id);

CREATE TABLE IF NOT EXISTS users_login_history (
	login_history_id  SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES ostm_users(user_id),
	login_timestamp timestamp(6) NULL,
	logout_timestamp timestamp(6) NULL,
	ip_address VARCHAR(45)
);
-- Index for faster queries
CREATE INDEX idx_users_login_history_user_id ON users_login_history(user_id);
CREATE INDEX idx_users_login_history_login_timestamp ON users_login_history(login_timestamp);
CREATE INDEX idx_users_login_history_ip_address ON users_login_history(ip_address);

CREATE TABLE IF NOT EXISTS users_password_reset_tokens (
	password_reset_token_id  SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES ostm_users(user_id),
	token VARCHAR(255) NOT NULL,
	expiration TIMESTAMP(6) NOT NULL,
	used BOOLEAN DEFAULT FALSE
);
-- Index for faster queries
CREATE INDEX idx_users_password_reset_tokens_user_id ON users_password_reset_tokens(user_id);
CREATE INDEX idx_users_password_reset_tokens_token ON users_password_reset_tokens(token);
CREATE INDEX idx_users_password_reset_tokens_expiration ON users_password_reset_tokens(expiration);
CREATE INDEX idx_users_password_reset_tokens_used ON users_password_reset_tokens(used);

CREATE TABLE IF NOT EXISTS users_two_factor_auth (
	two_factor_auth_id  SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES ostm_users(user_id),
	secret_key VARCHAR(255) NOT NULL,
	enabled BOOLEAN DEFAULT FALSE
);
-- Index for faster queries
CREATE INDEX idx_users_two_factor_auth_user_id ON users_two_factor_auth(user_id);
CREATE INDEX idx_users_two_factor_auth_enabled ON users_two_factor_auth(enabled);
CREATE INDEX idx_users_two_factor_auth_secret_key ON users_two_factor_auth(secret_key);

CREATE TABLE IF NOT EXISTS users_sessions (
	session_id  SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES ostm_users(user_id),
	session_token VARCHAR(255) NOT NULL,
	created_at TIMESTAMP(6) NOT NULL,
	expires_at TIMESTAMP(6) NOT NULL
);
-- Index for faster queries
CREATE INDEX idx_users_sessions_user_id ON users_sessions(user_id);
CREATE INDEX idx_users_sessions_session_token ON users_sessions(session_token);
CREATE INDEX idx_users_sessions_expires_at ON users_sessions(expires_at);
CREATE INDEX idx_users_sessions_created_at ON users_sessions(created_at);

CREATE TABLE IF NOT EXISTS users_roles_permissions (
	role_permission_id  SERIAL PRIMARY KEY,
	role VARCHAR(50) NOT NULL,
	permission VARCHAR(50) NOT NULL
);
-- Index for faster queries
CREATE INDEX idx_users_roles_permissions_role ON users_roles_permissions(role);
CREATE INDEX idx_users_roles_permissions_permission ON users_roles_permissions(permission);
CREATE INDEX idx_users_roles_permissions_role_permission ON users_roles_permissions(role, permission);

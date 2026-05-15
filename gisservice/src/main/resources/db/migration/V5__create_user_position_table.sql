-- User position tracking table
CREATE TABLE user_position (
    user_position_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    location GEOGRAPHY(POINT, 4326) NOT NULL,
    last_update TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Index for faster queries
CREATE INDEX idx_user_position_user_id ON user_position(user_id);
CREATE INDEX idx_user_position_last_update ON user_position(last_update);
CREATE INDEX idx_user_position_location ON user_position USING GIST(location);

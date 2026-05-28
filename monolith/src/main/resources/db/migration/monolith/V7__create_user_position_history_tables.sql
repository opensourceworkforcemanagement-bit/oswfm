-- user_current_position  : one row per user (upserted on every GPS update)
-- user_*_day_history_position : rolling history tables (7/31/61/91/180/366 days)
-- Triggers on user_current_position propagate every upsert into all history tables.
-- A single scheduled cleanup function prunes each history table to its retention window.

CREATE TABLE user_current_position (
    user_id       INTEGER                  NOT NULL PRIMARY KEY,
    location      GEOGRAPHY(POINT, 4326)   NOT NULL,
    last_update   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ucp_location    ON user_current_position USING GIST(location);
CREATE INDEX idx_ucp_last_update ON user_current_position(last_update);

CREATE TABLE user_7_day_history_position (
    history_id    SERIAL                   PRIMARY KEY,
    user_id       INTEGER                  NOT NULL,
    location      GEOGRAPHY(POINT, 4326)   NOT NULL,
    recorded_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_u7d_user_id     ON user_7_day_history_position(user_id);
CREATE INDEX idx_u7d_recorded_at ON user_7_day_history_position(recorded_at);
CREATE INDEX idx_u7d_location    ON user_7_day_history_position USING GIST(location);

CREATE TABLE user_31_day_history_position (
    history_id    SERIAL                   PRIMARY KEY,
    user_id       INTEGER                  NOT NULL,
    location      GEOGRAPHY(POINT, 4326)   NOT NULL,
    recorded_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_u31d_user_id     ON user_31_day_history_position(user_id);
CREATE INDEX idx_u31d_recorded_at ON user_31_day_history_position(recorded_at);
CREATE INDEX idx_u31d_location    ON user_31_day_history_position USING GIST(location);

CREATE TABLE user_61_day_history_position (
    history_id    SERIAL                   PRIMARY KEY,
    user_id       INTEGER                  NOT NULL,
    location      GEOGRAPHY(POINT, 4326)   NOT NULL,
    recorded_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_u61d_user_id     ON user_61_day_history_position(user_id);
CREATE INDEX idx_u61d_recorded_at ON user_61_day_history_position(recorded_at);
CREATE INDEX idx_u61d_location    ON user_61_day_history_position USING GIST(location);

CREATE TABLE user_91_day_history_position (
    history_id    SERIAL                   PRIMARY KEY,
    user_id       INTEGER                  NOT NULL,
    location      GEOGRAPHY(POINT, 4326)   NOT NULL,
    recorded_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_u91d_user_id     ON user_91_day_history_position(user_id);
CREATE INDEX idx_u91d_recorded_at ON user_91_day_history_position(recorded_at);
CREATE INDEX idx_u91d_location    ON user_91_day_history_position USING GIST(location);

CREATE TABLE user_180_day_history_position (
    history_id    SERIAL                   PRIMARY KEY,
    user_id       INTEGER                  NOT NULL,
    location      GEOGRAPHY(POINT, 4326)   NOT NULL,
    recorded_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_u180d_user_id     ON user_180_day_history_position(user_id);
CREATE INDEX idx_u180d_recorded_at ON user_180_day_history_position(recorded_at);
CREATE INDEX idx_u180d_location    ON user_180_day_history_position USING GIST(location);

CREATE TABLE user_366_day_history_position (
    history_id    SERIAL                   PRIMARY KEY,
    user_id       INTEGER                  NOT NULL,
    location      GEOGRAPHY(POINT, 4326)   NOT NULL,
    recorded_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_u366d_user_id     ON user_366_day_history_position(user_id);
CREATE INDEX idx_u366d_recorded_at ON user_366_day_history_position(recorded_at);
CREATE INDEX idx_u366d_location    ON user_366_day_history_position USING GIST(location);

CREATE OR REPLACE FUNCTION fn_propagate_position_to_history()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO user_7_day_history_position   (user_id, location, recorded_at) VALUES (NEW.user_id, NEW.location, NEW.last_update);
    INSERT INTO user_31_day_history_position  (user_id, location, recorded_at) VALUES (NEW.user_id, NEW.location, NEW.last_update);
    INSERT INTO user_61_day_history_position  (user_id, location, recorded_at) VALUES (NEW.user_id, NEW.location, NEW.last_update);
    INSERT INTO user_91_day_history_position  (user_id, location, recorded_at) VALUES (NEW.user_id, NEW.location, NEW.last_update);
    INSERT INTO user_180_day_history_position (user_id, location, recorded_at) VALUES (NEW.user_id, NEW.location, NEW.last_update);
    INSERT INTO user_366_day_history_position (user_id, location, recorded_at) VALUES (NEW.user_id, NEW.location, NEW.last_update);
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_propagate_position_to_history
AFTER INSERT OR UPDATE ON user_current_position
FOR EACH ROW EXECUTE FUNCTION fn_propagate_position_to_history();

CREATE OR REPLACE FUNCTION fn_cleanup_position_history()
RETURNS void LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM user_7_day_history_position   WHERE recorded_at < NOW() - INTERVAL '7 days';
    DELETE FROM user_31_day_history_position  WHERE recorded_at < NOW() - INTERVAL '31 days';
    DELETE FROM user_61_day_history_position  WHERE recorded_at < NOW() - INTERVAL '61 days';
    DELETE FROM user_91_day_history_position  WHERE recorded_at < NOW() - INTERVAL '91 days';
    DELETE FROM user_180_day_history_position WHERE recorded_at < NOW() - INTERVAL '180 days';
    DELETE FROM user_366_day_history_position WHERE recorded_at < NOW() - INTERVAL '366 days';
END;
$$;

CREATE TABLE otps (
                      id BIGINT PRIMARY KEY,
                      otp VARCHAR(6),
                      expiration_time TIMESTAMP,
                      user_id BIGINT
);

ALTER TABLE users ADD COLUMN is_verified BOOLEAN;

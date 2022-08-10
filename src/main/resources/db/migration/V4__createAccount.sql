CREATE TABLE IF NOT EXISTS account
(
    id              SERIAL PRIMARY KEY,
    title           VARCHAR(128) NOT NULL UNIQUE,
    user_id         BIGINT
);

CREATE TABLE IF NOT EXISTS currency
(
    currency_id          SERIAL PRIMARY KEY,
    currency_title       VARCHAR(3) NOT NULL UNIQUE,
    currency_meaning     VARCHAR(128) NOT NULL,
    currency_quantity    BIGINT
);

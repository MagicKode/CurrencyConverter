CREATE TABLE IF NOT EXISTS bank_transaction
(
    id                  SERIAL PRIMARY KEY,
    value               BIGINT NOT NULL,
    operation           BIGINT NOT NULL,
    bank_account_id     BIGINT NOT NULL,
    FOREIGN KEY (bank_account_id) REFERENCES bank_account (id)
);

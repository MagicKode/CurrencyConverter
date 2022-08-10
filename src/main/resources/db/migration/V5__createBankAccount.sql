CREATE TABLE IF NOT EXISTS bank_account
(
    id              SERIAL PRIMARY KEY,
    value           BIGINT NOT NULL,
    curr_id         BIGINT NOT NULL,
    account_id      BIGINT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id)
);

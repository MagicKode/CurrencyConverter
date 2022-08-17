ALTER TABLE bank_account
DROP COLUMN currency_title,
ADD COLUMN currency_id BIGINT,
ADD FOREIGN KEY (currency_id) REFERENCES currency (currency_id)
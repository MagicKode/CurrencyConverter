ALTER TABLE bank_account
DROP COLUMN currency_id,
ADD COLUMN currency_title VARCHAR(3);

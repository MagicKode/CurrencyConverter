ALTER TABLE rate
ALTER COLUMN rate_type TYPE INT
USING rate_type::integer;
-- DROP COLUMN rate_type,
-- ADD COLUMN rate_type int NOT NULL
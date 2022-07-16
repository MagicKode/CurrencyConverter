CREATE TABLE IF NOT EXISTS rate
(
    rate_id             SERIAL PRIMARY KEY,
    rate_value          BIGINT,
    rate_type           VARCHAR(128) NOT NULL,
    id_currency_from    int NOT NULL,
    id_currency_to      int NOT NULL,
    created_at          TIMESTAMP DEFAULT NOW(),
    updated_at          TIMESTAMP DEFAULT NOW(),
    CONSTRAINT rate_currency_id_1
        FOREIGN KEY (id_currency_from) REFERENCES currency (currency_id),
    CONSTRAINT rate_currency_id_2
        FOREIGN KEY (id_currency_to) REFERENCES currency (currency_id)
);





-- id_currency_from & id_currency_to & rateType(in or out) -> unique
    -- 1, 22, "BUYINGRATE", 1, 2 //rub -> usd
    -- 2, 33, "SELLINGRATE", 2, 1 // usd -> rub
    -- 3, 1, "BUYINGRATE", 1, 3 // bun -> usd
    -- 4, 1, "SELLINGRATE", 3, 1 // usd -> bun

    -- 1, USD, AMERICAN DOLLAR
    -- 2, RUB, RUSSIAN RUBLE
    -- 3, BUN, BEL RUBLE

    -- select * from rate inner join currency on id_currency_to = currency_id where rateType = 'BUYINGRATE'


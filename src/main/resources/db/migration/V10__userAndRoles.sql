CREATE TABLE IF NOT EXISTS role_table
(
    id          SERIAL PRIMARY KEY,
    role_name   VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_table
(
    id       SERIAL PRIMARY KEY,
    login    varchar(50),
    password varchar(500),
    role_id  integer
 constraint user_table_role_table_id_fk references role_table
);

create unique index user_table_login_uindex on user_table (login);

insert into role_table(role_name) values ('ROLE_ADMIN');
insert into role_table(role_name) values ('ROLE_USER');
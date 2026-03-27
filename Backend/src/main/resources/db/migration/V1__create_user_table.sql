create table users (
    id VARCHAR(255) PRIMARY KEY,
    email varchar(255) not null unique,
    password varchar(255) not null,
    full_name varchar(255) not null
);

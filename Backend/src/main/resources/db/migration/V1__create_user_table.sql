create table users (
    id uuid primary key,
    email varchar(255) not null unique,
    password varchar(255) not null,
    full_name varchar(255) not null
);

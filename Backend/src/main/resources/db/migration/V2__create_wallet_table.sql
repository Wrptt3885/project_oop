create table wallets (
    id uuid primary key,
    user_id uuid not null unique,
    balance decimal(19, 2) not null,
    currency varchar(16) not null,
    constraint fk_wallet_user foreign key (user_id) references users (id)
);

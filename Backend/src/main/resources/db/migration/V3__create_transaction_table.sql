create table transactions (
    id uuid primary key,
    source_wallet_id uuid not null,
    target_wallet_id uuid not null,
    amount decimal(19, 2) not null,
    type varchar(32) not null,
    status varchar(32) not null,
    reference varchar(255) not null,
    created_at timestamp with time zone not null,
    constraint fk_transaction_source_wallet foreign key (source_wallet_id) references wallets (id),
    constraint fk_transaction_target_wallet foreign key (target_wallet_id) references wallets (id)
);
create table wallets (
    id uuid primary key,
    user_id varchar(255) not null unique, -- <--- ต้องเปลี่ยนเป็น varchar ให้ตรงกับ users.id
    balance decimal(19, 2) not null,
    currency varchar(16) not null,
    constraint fk_wallet_user foreign key (user_id) references users (id)
);
create table member
(
    id              bigint      not null auto_increment,
    email           varchar(50) not null,
    password        varchar(30) not null,
    nick_name       varchar(30) not null,
    created_at      datetime(6) not null default now(6),
    updated_at      datetime(6) not null default now(6),
    last_login_at   datetime(6),
    unregistered_at datetime(6),
    user_role       varchar(10) not null,
    primary key (id)
);

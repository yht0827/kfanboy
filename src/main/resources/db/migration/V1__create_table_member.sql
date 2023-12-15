# member 테이블
create table member
(
    id              bigint       not null auto_increment,
    email           varchar(50)  not null unique,
    password        varchar(100) not null,
    nick_name       varchar(30)  not null unique,
    created_at      datetime(6)  not null default now(6),
    updated_at      datetime(6)  not null default now(6),
    last_login_at   datetime(6),
    unregistered_at datetime(6),
    user_role       varchar(10)  not null default 'ROLE_USER',
    is_deleted      tinyint(1)   not null default 0,
    primary key (id)
);

# 관리자 아이디 추가
INSERT INTO member (id, email, password, nick_name, created_at, updated_at, last_login_at, unregistered_at, user_role)
    value (1, 'admin@kfanboy.com', '$2a$10$JmTxpcJrNRsqcVZoBZqgiOKdSkfYSXIOf6YDoVZGWz9iVkSDE/NgC', 'manager', now(6),
           now(6), null, null, 'ROLE_ADMIN');
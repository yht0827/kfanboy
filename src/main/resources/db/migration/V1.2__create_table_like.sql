# Like 테이블
create table board_like
(
    like_id    bigint      not null auto_increment,
    board_id   bigint      not null,
    member_id  bigint      not null,
    deleted_at datetime(6)          default null,
    updated_at datetime(6) not null default now(6),
    primary key (like_id)
);

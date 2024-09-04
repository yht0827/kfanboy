# board 테이블
CREATE table board
(
    board_id      bigint       not null auto_increment,
    title         varchar(100) not null,
    content       TEXT         not null,
    comment_count integer      not null,
    like_count    integer      not null,
    view_count    integer      not null,
    created_at    datetime(6)  not null default now(6),
    updated_at    datetime(6)  not null default now(6),
    deleted_at    datetime(6)           default null,
    member_id     bigint       not null,
    category_id   bigint       not null,
    primary key (board_id)
);

# category 테이블
create table category
(
    category_id   bigint      not null auto_increment,
    category_name varchar(50) not null,
    created_at    datetime(6) not null default now(6),
    updated_at    datetime(6) not null default now(6),
    primary key (category_id)
);

# 카테고리 추가
INSERT INTO category (category_name) value ('K-pop');
INSERT INTO category (category_name) value ('Actor');
INSERT INTO category (category_name) value ('Youtuber');
INSERT INTO category (category_name) value ('Comedian');
INSERT INTO category (category_name) value ('Celebrity');

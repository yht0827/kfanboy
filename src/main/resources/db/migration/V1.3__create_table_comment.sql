create table comment
(
    comment_id          bigint       not null auto_increment,
    content             varchar(255) not null,
    child_count         integer      not null,
    comment_group       integer      not null,
    comment_group_order integer      not null,
    comment_level       integer      not null,
    board_id            bigint       not null,
    parent_id           bigint       not null,
    created_at          datetime(6)  not null default now(6),
    updated_at          datetime(6)  not null default now(6),
    deleted_at          datetime(6),
    primary key (comment_id)
);
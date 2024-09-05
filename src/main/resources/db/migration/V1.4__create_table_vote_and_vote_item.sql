CREATE table vote
(
    vote_id     bigint       not null auto_increment,
    title       varchar(100) not null,
    vote_count  integer      not null,
    is_finished boolean      not null,
    start_at    datetime(6)  not null default now(6),
    end_at      datetime(6)           default null,
    created_at  datetime(6)  not null default now(6),
    updated_at  datetime(6)  not null default now(6),
    deleted_at  datetime(6)           default null,
    member_id   bigint       not null,
    primary key (vote_id)
);
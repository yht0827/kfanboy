CREATE table vote
(
    vote_id     bigint      not null auto_increment,
    title       varchar(50) not null,
    vote_count  integer     not null,
    is_finished boolean     not null,
    start_at    datetime(6)  not null default now(6),
    end_at      datetime(6)           default null,
    created_at  datetime(6)  not null default now(6),
    updated_at  datetime(6)  not null default now(6),
    deleted_at  datetime(6)           default null,
    member_id   bigint      not null,
    primary key (vote_id)
);

CREATE table vote_item
(
    vote_item_id bigint      not null auto_increment,
    item_content varchar(50) not null,
    item_count   integer     not null,
    vote_id      bigint      not null,
    primary key (vote_item_id)
);
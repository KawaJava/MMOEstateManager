--liquibase formatted sql
--changeset kawajava:5
create table borough (
    id bigint not null auto_increment primary key,
    name varchar(255) unique not null,
    country_id bigint not null,
    slug varchar(255) unique not null,
    actual_leader_id bigint not null,
    leader_start_date datetime not null,
    actual_gold decimal(9,2) not null,
    gold_added_by bigint not null,
    date_added datetime not null,
    email_send boolean not null,
    constraint fk_borough_country_id foreign key (country_id) references country(id),
    constraint fk_actual_leader_id foreign key (actual_leader_id) references player(id),
    constraint fk_gold_added_by foreign key (gold_added_by) references player(id)
);
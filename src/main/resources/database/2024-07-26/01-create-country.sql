--liquibase formatted sql
--changeset kawajava:4
create table country (
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(255) unique not null,
    slug varchar(255) unique not null,
    actual_sheriff_id bigint,
    gold_limit decimal(9,2) not null,
    sheriff_start_date datetime not null,
    constraint fk_actual_sheriff_id foreign key (actual_sheriff_id) references player(id)
);
--liquibase formatted sql
--changeset kawajava:1
create table player (
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(255) unique not null,
    email varchar(255) unique not null,
    clan varchar(255) not null,
    isActive boolean default true not null,
    created date not null
);
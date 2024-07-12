--liquibase formatted sql
--changeset kawajava:2
alter table player add slug varchar(255) not null after created;
alter table player add constraint ui_player_slug unique key(slug);
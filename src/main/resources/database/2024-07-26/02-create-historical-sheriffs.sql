--liquibase formatted sql
--changeset kawajava:5
create table historical_sheriffs (
    id bigint not null auto_increment PRIMARY KEY,
    country_id bigint not null,
    player_id bigint not null,
    start_date datetime not null,
    end_date datetime not null,
    constraint fk_country_id foreign key (country_id) references country(id),
    constraint fk_player_id foreign key (player_id) references player(id)
);
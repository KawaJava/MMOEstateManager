--liquibase formatted sql
 --changeset kawajava:6
 create table historical_leaders (
     id bigint not null auto_increment PRIMARY KEY,
     borough_id bigint not null,
     player_id bigint not null,
     start_date datetime not null,
     end_date datetime not null,
     constraint fk_borough_id foreign key (borough_id) references borough(id),
     constraint fk_leader_id foreign key (player_id) references player(id)
 );
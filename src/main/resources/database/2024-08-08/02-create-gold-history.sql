--liquibase formatted sql
 --changeset kawajava:7
 create table gold_history (
     id bigint not null auto_increment PRIMARY KEY,
     borough_id bigint not null,
     gold decimal(9,2) not null,
     gold_added_by bigint not null,
     date_added datetime not null,
     email_send boolean not null,
     constraint fk_borough_id_7 foreign key (borough_id) references borough(id),
     constraint fk_leader_id_7 foreign key (gold_added_by) references player(id)
 );
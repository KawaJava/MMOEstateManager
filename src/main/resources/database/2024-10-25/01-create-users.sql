--liquibase formatted sql
--changeset kawajava:8
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    player_id BIGINT,
    CONSTRAINT fk_users_player_id FOREIGN KEY (player_id) REFERENCES player(id)
);

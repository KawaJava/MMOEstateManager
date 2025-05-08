--liquibase formatted sql
--changeset kawajava:9
CREATE TABLE player_review (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    author_name VARCHAR(60) NOT NULL,
    note TINYINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    accepted BOOLEAN NOT NULL,
    CONSTRAINT fk_player_review_player_id FOREIGN KEY (player_id) REFERENCES player(id)
);

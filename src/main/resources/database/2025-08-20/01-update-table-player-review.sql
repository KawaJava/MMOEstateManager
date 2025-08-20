--liquibase formatted sql
--changeset kawajava:10
ALTER TABLE player_review
    ADD COLUMN ai_opinion VARCHAR(20) NULL
    AFTER created_at
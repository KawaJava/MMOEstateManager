--liquibase formatted sql
--changeset kawajava:11
ALTER TABLE historical_sheriffs RENAME TO historical_sheriff;
ALTER TABLE historical_leaders RENAME TO historical_leader;

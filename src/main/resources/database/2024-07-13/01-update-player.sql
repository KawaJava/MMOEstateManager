--liquibase formatted sql
--changeset kawajava:3
ALTER TABLE `mmoestatemanager`.`player`
CHANGE COLUMN `isActive` `is_active` TINYINT(1) NOT NULL DEFAULT '1' ;
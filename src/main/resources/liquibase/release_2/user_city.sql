--liquibase formatted sql
--changeset macielos:release_2.user_city.sql

alter table users
add column city varchar(200);

--rollback alter table users drop column city;
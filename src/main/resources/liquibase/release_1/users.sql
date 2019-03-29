--liquibase formatted sql
--changeset macielos:release_1.user.sql

create table users
(
 id bigserial NOT NULL,
 name varchar(200) not null,
 registrationdate date not null,
 CONSTRAINT pk_user_id PRIMARY KEY (id)
 );

--rollback drop table users;
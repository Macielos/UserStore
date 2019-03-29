--liquibase formatted sql
--changeset macielos:release_3.user_credentials.sql

create table user_credentials
(
 id bigserial not null,
 name varchar(200) not null,
 password_hash varchar(200) not null
  CONSTRAINT pk_user_credentials_id PRIMARY KEY (id)
  );

--rollback drop table user_credentials;
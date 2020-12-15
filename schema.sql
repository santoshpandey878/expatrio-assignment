--create database usermanagement;

\connect usermanagement;

create table users(
	id bigserial PRIMARY KEY,
	first_name varchar(50),
	last_name varchar(50),
	user_name varchar(20),
	password varchar(256),
	email varchar(100),
	phone varchar(20)
);

create table roles(
	id bigserial PRIMARY KEY,
	name varchar(50),
	description varchar(256)
);

create table users_roles(
	user_id integer references users(id),
	role_id integer references roles(id)
);

INSERT INTO ROLES (NAME,DESCRIPTION) VALUES ('ROLE_CUSTOMER', 'customer role');
INSERT INTO ROLES (NAME,DESCRIPTION) VALUES ('ROLE_ADMIN', 'admin role');

INSERT INTO USERS(first_name,last_name,user_name,password,email,phone) VALUES ('S','P','admin','$2y$12$hDw.GLM7Bdy4eYXfCls1DOPK.c3GtU2lTsYPPCXG.jbn3nI1Bw/Ma','SP878@GMAIL.COM','9918980100');
INSERT INTO users_roles(user_id,role_id) VALUES (1,2);


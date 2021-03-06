drop table if exists items;
drop table if exists roles;
drop table if exists statuses;
drop table if exists users;
drop table if exists receipts;

drop schema if exists cashierSystem;
create schema cashierSystem;

create table cashierSystem.items
(
    id       int auto_increment
        primary key,
    name     varchar(255) not null,
    quantity int          not null,
    weight   double       not null,
    constraint items_name_uindex
        unique (name)
);

create table cashierSystem.roles
(
    id   int auto_increment
        primary key,
    role varchar(15) null
);

create table cashierSystem.statuses
(
    id     int auto_increment
        primary key,
    Status varchar(15) not null
);

create table cashierSystem.users
(
    id       int auto_increment
        primary key,
    username varchar(20)  not null,
    email    varchar(100) not null,
    password varchar(100) not null,
    role_id  int          not null,
    constraint users_email_uindex
        unique (email),
    constraint users_username_uindex
        unique (username),
    constraint users_roles_id_fk
        foreign key (role_id) references roles (id)
);

create table cashierSystem.receipts
(
    id        int auto_increment
        primary key,
    status_id int not null,
    user_id   int null,
    item_id   int not null,
    constraint receipts_items_id_fk
        foreign key (item_id) references items (id),
    constraint receipts_statuses_id_fk
        foreign key (status_id) references statuses (id),
    constraint receipts_users_id_fk
        foreign key (user_id) references users (id)
);

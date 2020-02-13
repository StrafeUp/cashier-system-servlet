drop table if exists receipts;
drop table if exists statuses;
drop table if exists users;
drop table if exists roles;
drop table if exists items;

create table items
(
    id       int auto_increment primary key,
    name     varchar(255) not null,
    quantity int          not null,
    weight   double       not null,
    constraint items_name_uindex unique (name)
);

create table roles
(
    id   int auto_increment
        primary key,
    role varchar(15) null
);

create table statuses
(
    id     int auto_increment
        primary key,
    Status varchar(15) not null
);

create table users
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

create table receipts
(
    id              int auto_increment primary key,
    receipt_id      int      not null,
    status_id       int      not null,
    user_id         int      null,
    item_id         int      not null,
    time_of_receipt datetime,
    constraint receipts_items_id_fk
        foreign key (item_id) references items (id),
    constraint receipts_statuses_id_fk
        foreign key (status_id) references statuses (id),
    constraint receipts_users_id_fk
        foreign key (user_id) references users (id)
);
insert into items (id, name, weight, quantity) values (1, 'Kumquat', 417.5, 2);
insert into items (id, name, weight, quantity) values (2, 'Iced Tea - Lemon, 340ml', 202.3, 28);
insert into items (id, name, weight, quantity) values (3, 'Bread - White Mini Epi', 548.7, 15);
insert into items (id, name, weight, quantity) values (4, 'Wine - Hardys Bankside Shiraz', 971.7, 22);
insert into items (id, name, weight, quantity) values (5, 'Nut - Macadamia', 867.7, 55);
insert into items (id, name, weight, quantity) values (6, 'Cookies - Englishbay Wht', 339.7, 21);
insert into items (id, name, weight, quantity) values (7, 'Creme De Banane - Marie', 203.5, 64);
insert into items (id, name, weight, quantity) values (8, 'Bay Leaf Fresh', 484.3, 12);
insert into items (id, name, weight, quantity) values (9, 'Bread - Hamburger Buns', 399.2, 84);
insert into items (id, name, weight, quantity) values (10, 'Wine - Sicilia Igt Nero Avola', 24.1, 1);
insert into items (id, name, weight, quantity) values (11, 'Appetizer - Seafood Assortment', 516.5, 75);
insert into items (id, name, weight, quantity) values (12, 'Nut - Almond, Blanched, Whole', 748.3, 80);
insert into items (id, name, weight, quantity) values (13, 'Lobak', 381.7, 20);
insert into items (id, name, weight, quantity) values (14, 'Cake - Lemon Chiffon', 698.1, 84);
insert into items (id, name, weight, quantity) values (15, 'Soup - Canadian Pea, Dry Mix', 476.0, 16);
insert into items (id, name, weight, quantity) values (16, 'Wine - Tribal Sauvignon', 47.8, 8);
insert into items (id, name, weight, quantity) values (17, 'Cheese - Pied De Vents', 960.1, 98);
insert into items (id, name, weight, quantity) values (18, 'Tart Shells - Sweet, 2', 626.1, 78);
insert into items (id, name, weight, quantity) values (19, 'Coke - Diet, 355 Ml', 7.2, 30);
insert into items (id, name, weight, quantity) values (20, 'Caviar - Salmon', 202.3, 80);

INSERT INTO roles (id, role) VALUES (1, 'ADMIN');
INSERT INTO roles (id, role) VALUES (2, 'MERCHANDISER');
INSERT INTO roles (id, role) VALUES (3, 'MANAGER');
INSERT INTO roles (id, role) VALUES (4, 'CASHIER');
INSERT INTO roles (id, role) VALUES (5, 'DEFAULT');

INSERT INTO statuses (id, Status) VALUES (1, 'OPEN');
INSERT INTO statuses (id, Status) VALUES (2, 'IN_DISPUTE');
INSERT INTO statuses (id, Status) VALUES (3, 'CLOSED');

INSERT INTO users (id, username, email, password, role_id) VALUES (1, 'Hello123', 'Hello@gmail.com', 'sagdhlsajb', 2);
INSERT INTO users (id, username, email, password, role_id) VALUES (3, 'Hell12o123', 'Hello2@gmail.com', 'sagdhlsajb', 2);

INSERT INTO receipts (id, receipt_id, status_id, user_id, item_id, time_of_receipt) VALUES (1, 1, 3, 1, 3, '2020-02-08 10:16:40');
INSERT INTO receipts (id, receipt_id, status_id, user_id, item_id, time_of_receipt) VALUES (2, 1, 3, 1, 2, '2020-02-08 10:16:40');
INSERT INTO receipts (id, receipt_id, status_id, user_id, item_id, time_of_receipt) VALUES (3, 1, 3, 1, 5, '2020-02-08 10:16:40');
INSERT INTO receipts (id, receipt_id, status_id, user_id, item_id, time_of_receipt) VALUES (4, 1, 3, 1, 10, '2020-02-08 10:16:40');
INSERT INTO receipts (id, receipt_id, status_id, user_id, item_id, time_of_receipt) VALUES (5, 2, 2, 3, 2, '2020-02-08 12:16:40');
INSERT INTO receipts (id, receipt_id, status_id, user_id, item_id, time_of_receipt) VALUES (6, 2, 2, 3, 15, '2020-02-08 12:16:40');
INSERT INTO receipts (id, receipt_id, status_id, user_id, item_id, time_of_receipt) VALUES (7, 3, 1, 1, 20, '2020-02-08 14:16:40');



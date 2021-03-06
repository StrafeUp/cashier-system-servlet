insert into cashierSystem.items (id, name, weight, quantity) values (1, 'Kumquat', 417.5, 2);
insert into cashierSystem.items (id, name, weight, quantity) values (2, 'Iced Tea - Lemon, 340ml', 202.3, 28);
insert into cashierSystem.items (id, name, weight, quantity) values (3, 'Bread - White Mini Epi', 548.7, 15);
insert into cashierSystem.items (id, name, weight, quantity) values (4, 'Wine - Hardys Bankside Shiraz', 971.7, 22);
insert into cashierSystem.items (id, name, weight, quantity) values (5, 'Nut - Macadamia', 867.7, 55);
insert into cashierSystem.items (id, name, weight, quantity) values (6, 'Cookies - Englishbay Wht', 339.7, 21);
insert into cashierSystem.items (id, name, weight, quantity) values (7, 'Creme De Banane - Marie', 203.5, 64);
insert into cashierSystem.items (id, name, weight, quantity) values (8, 'Bay Leaf Fresh', 484.3, 12);
insert into cashierSystem.items (id, name, weight, quantity) values (9, 'Bread - Hamburger Buns', 399.2, 84);
insert into cashierSystem.items (id, name, weight, quantity) values (10, 'Wine - Sicilia Igt Nero Avola', 24.1, 1);
insert into cashierSystem.items (id, name, weight, quantity) values (11, 'Appetizer - Seafood Assortment', 516.5, 75);
insert into cashierSystem.items (id, name, weight, quantity) values (12, 'Nut - Almond, Blanched, Whole', 748.3, 80);
insert into cashierSystem.items (id, name, weight, quantity) values (13, 'Lobak', 381.7, 20);
insert into cashierSystem.items (id, name, weight, quantity) values (14, 'Cake - Lemon Chiffon', 698.1, 84);
insert into cashierSystem.items (id, name, weight, quantity) values (15, 'Soup - Canadian Pea, Dry Mix', 476.0, 16);
insert into cashierSystem.items (id, name, weight, quantity) values (16, 'Wine - Tribal Sauvignon', 47.8, 8);
insert into cashierSystem.items (id, name, weight, quantity) values (17, 'Cheese - Pied De Vents', 960.1, 98);
insert into cashierSystem.items (id, name, weight, quantity) values (18, 'Tart Shells - Sweet, 2', 626.1, 78);
insert into cashierSystem.items (id, name, weight, quantity) values (19, 'Coke - Diet, 355 Ml', 7.2, 30);
insert into cashierSystem.items (id, name, weight, quantity) values (20, 'Caviar - Salmon', 202.3, 80);

INSERT INTO cashierSystem.roles (id, role) VALUES (1, 'ADMIN');
INSERT INTO cashierSystem.roles (id, role) VALUES (2, 'MERCHANDISER');
INSERT INTO cashierSystem.roles (id, role) VALUES (3, 'MANAGER');
INSERT INTO cashierSystem.roles (id, role) VALUES (4, 'CASHIER');

INSERT INTO cashierSystem.statuses (id, Status) VALUES (1, 'OPEN');
INSERT INTO cashierSystem.statuses (id, Status) VALUES (2, 'IN_DISPUTE');
INSERT INTO cashierSystem.statuses (id, Status) VALUES (3, 'CLOSED');

INSERT INTO cashierSystem.receipts (id, status_id, user_id, item_id) VALUES (1, 3, 1, 3);

INSERT INTO cashierSystem.users (id, username, email, password, role_id) VALUES (1, 'Hello123', 'Hello@gmail.com', 'sagdhlsajb ', 2);
INSERT INTO cashierSystem.users (id, username, email, password, role_id) VALUES (3, 'Hell12o123', 'Hello2@gmail.com', 'sagdhlsajb ', 2);
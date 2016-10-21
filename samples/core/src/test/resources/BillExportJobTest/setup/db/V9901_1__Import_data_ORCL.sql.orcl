--Rename this file to V9901_1__Import_data_ORCL.sql.orcl if the database used is not Oracle 11g

INSERT INTO RESTAURANTTABLE (id, modificationCounter, "number", state) VALUES (101, 1, 1, 2);
INSERT INTO RESTAURANTTABLE (id, modificationCounter, "number", state) VALUES (102, 1, 2, 0);
INSERT INTO RESTAURANTTABLE (id, modificationCounter, "number", state) VALUES (103, 1, 3, 0);
INSERT INTO RESTAURANTTABLE (id, modificationCounter, "number", state) VALUES (104, 1, 4, 0);
INSERT INTO RESTAURANTTABLE (id, modificationCounter, "number", state) VALUES (105, 1, 5, 0);

INSERT INTO PRODUCT (id, modificationCounter, dtype, description) VALUES (1, 1, 'Meal', 'Schnitzel');
INSERT INTO PRODUCT (id, modificationCounter, dtype, description) VALUES (2, 1, 'Meal', 'Goulasch');
INSERT INTO PRODUCT (id, modificationCounter, dtype, description) VALUES (3, 1, 'Meal', 'Pfifferlinge');
INSERT INTO PRODUCT (id, modificationCounter, dtype, description) VALUES (4, 1, 'Meal', 'Salat');

INSERT INTO PRODUCT (id, modificationCounter, dtype, description) VALUES (5, 1, 'SideDish', 'Pommes');
INSERT INTO PRODUCT (id, modificationCounter, dtype, description) VALUES (6, 1, 'SideDish', 'Reis');
INSERT INTO PRODUCT (id, modificationCounter, dtype, description) VALUES (7, 1, 'SideDish', 'Brot');
INSERT INTO PRODUCT (id, modificationCounter, dtype, description) VALUES (8, 1, 'SideDish', 'Knödel');

INSERT INTO PRODUCT (id, modificationCounter, dtype, description, alcoholic) VALUES (9, 1, 'Drink', 'Wasser', 0);
INSERT INTO PRODUCT (id, modificationCounter, dtype, description, alcoholic) VALUES (10, 1, 'Drink', 'Cola', 0);
INSERT INTO PRODUCT (id, modificationCounter, dtype, description, alcoholic) VALUES (11, 1, 'Drink', 'Bier', 0);
INSERT INTO PRODUCT (id, modificationCounter, dtype, description, alcoholic) VALUES (12, 1, 'Drink', 'Wein / Apfelwein', 0);

INSERT INTO OFFER (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (1, 1, 'Schnitzel-Menü', 'Description of Schnitzel-Menü', 0, 1, 5, 10, 6.99);
INSERT INTO OFFER (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (2, 1, 'Goulasch-Menü', 'Description of Goulasch-Menü', 0, 2, 6, 11, 7.99);
INSERT INTO OFFER (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (3, 1, 'Pfifferlinge-Menü', 'Description of Pfifferlinge-Menü', 0, 3, 8, 12, 8.99);
INSERT INTO OFFER (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (4, 1, 'Salat-Menü', 'Description of Salat-Menü', 0, 4, 7, 9, 5.99);
INSERT INTO OFFER (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (5, 1, 'Cola', 'Description of Cola-Menü', 0, null, null, 10, 1.20);

INSERT INTO RESTAURANTORDER (id, modificationCounter, table_id, state) VALUES (1, 1, 101, 1);

INSERT INTO ORDERPOSITION (id, modificationCounter, offer_id, offername, "comment", state, drinkState, order_id, price) VALUES (1, 1, 1, 'Schnitzel-Menü', 'mit Ketschup', 2, 2, 1, 6.99);
INSERT INTO ORDERPOSITION (id, modificationCounter, offer_id, offername, "comment", state, drinkState, order_id, price) VALUES (2, 1, 2, 'Goulasch-Menü', '', 2, 2, 1, 7.99);
INSERT INTO ORDERPOSITION (id, modificationCounter, offer_id, offername, "comment", state, drinkState, order_id, price) VALUES (3, 1, 3, 'Pfifferlinge-Menü','', 2, 2,  1, 8.99);
INSERT INTO ORDERPOSITION (id, modificationCounter, offer_id, offername, "comment", state, drinkState, order_id, price) VALUES (4, 1, 4, 'Salat-Menü', '', 2, 2, 1, 5.99);
INSERT INTO ORDERPOSITION (id, modificationCounter, offer_id, offername, "comment", state, drinkState, order_id, price) VALUES (5, 1, 5, 'Cola', '', 2, 2, 1, 5.99);

INSERT INTO BILL (id, modificationCounter, payed, total, tip) VALUES (1, 1, 1, 14.98, 1.3);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (1,1);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (1,2);

INSERT INTO BILL (id, modificationCounter, payed, total,tip) VALUES (2, 1, 1, 14.98, 1.4);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (2,3);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (2,4);

INSERT INTO BILL (id, modificationCounter, payed, total,tip) VALUES (3, 1, 1, 15.98, 1.5);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (2,3);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (2,4);
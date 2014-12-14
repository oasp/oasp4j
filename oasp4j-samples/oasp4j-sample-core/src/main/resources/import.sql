INSERT INTO RestaurantTable (id, number, modificationCounter, state) VALUES (101, 1, 1, 2);
INSERT INTO RestaurantTable (id, number, modificationCounter, state) VALUES (102, 2, 1, 0);
INSERT INTO RestaurantTable (id, number, modificationCounter, state) VALUES (103, 3, 1, 0);
INSERT INTO RestaurantTable (id, number, modificationCounter, state) VALUES (104, 4, 1, 0);
INSERT INTO RestaurantTable (id, number, modificationCounter, state) VALUES (105, 5, 1, 0);

INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES (1, 'Meal', 'Schnitzel', 1);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES (2, 'Meal', 'Goulasch', 1);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES (3, 'Meal', 'Pfifferlinge', 1);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES (4, 'Meal', 'Salat', 1);

INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES (5, 'SideDish', 'Pommes', 1);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES (6, 'SideDish', 'Reis', 1);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES (7, 'SideDish', 'Brot', 1);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter) VALUES (8, 'SideDish', 'Knödel', 1);

INSERT INTO PRODUCT (id, dtype, description, modificationCounter, alcoholic) VALUES (9, 'Drink', 'Wasser', 1, false);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter, alcoholic) VALUES (10, 'Drink', 'Cola', 1, false);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter, alcoholic) VALUES (11, 'Drink', 'Bier', 1, false);
INSERT INTO PRODUCT (id, dtype, description, modificationCounter, alcoholic) VALUES (12, 'Drink', 'Wein / Apfelwein', 1, false);

INSERT INTO OFFER (id, description, state, meal_id, sidedish_id, drink_id, modificationCounter, currentprice) VALUES (1, 'Schnitzel-Menü', 0, 1, 5, 10, 1, 6.99);
INSERT INTO OFFER (id, description, state, meal_id, sidedish_id, drink_id, modificationCounter, currentprice) VALUES (2, 'Goulasch-Menü', 0, 2, 6, 11, 1, 7.99);
INSERT INTO OFFER (id, description, state, meal_id, sidedish_id, drink_id, modificationCounter, currentprice) VALUES (4, 'Pfifferlinge-Menü', 0, 3, 8, 12, 1, 8.99);
INSERT INTO OFFER (id, description, state, meal_id, sidedish_id, drink_id, modificationCounter, currentprice) VALUES (5, 'Salat-Menü', 0, 4, 7, 9, 1, 5.99);

INSERT INTO TABLEORDER (id, tableid,  modificationCounter, state) VALUES (1, 1, 1,1);

INSERT INTO ORDERPOSITION (id, offername, comment, state, order_Id, modificationCounter, price) VALUES (1, 'Schnitzel-Menü', 'mit Ketschup', 2, 1, 1, 6.99);
INSERT INTO ORDERPOSITION (id, offername, comment, state, order_Id, modificationCounter, price) VALUES (2, 'Goulasch-Menü', '', 2, 1, 1, 7.99);
INSERT INTO ORDERPOSITION (id, offername, comment, state, order_Id, modificationCounter, price) VALUES (3, 'Pfifferlinge-Menü','', 2, 1, 1, 8.99);
INSERT INTO ORDERPOSITION (id, offername, comment, state, order_Id, modificationCounter, price) VALUES (4, 'Salat-Menü', '', 2, 1, 1, 5.99);

INSERT INTO BILL (id, payed, modificationCounter, totalamount,tip) VALUES (1, true, 1, 14.98, 1.3);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (1,1);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (1,2);

INSERT INTO BILL (id, payed, modificationCounter, totalamount,tip) VALUES (2, true, 1, 14.98, 1.4);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (2,3);
INSERT INTO BILL_ORDERPOSITION (bill_id, orderpositions_id) VALUES (2,4);

INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (0, 'chief', 0, 'Charly', 'Chief', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (1, 'cook', 1, 'Carl', 'Cook', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (2, 'waiter', 2, 'Willy', 'Waiter', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (3, 'barkeeper', 3, 'Bianca', 'Barkeeper', 0);

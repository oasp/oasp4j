-- Rename this file to V9901_1__Import_data_MYSQL.sql.mysql if the database used is not MariaDB 10.0.27

INSERT INTO RESTAURANTTABLE (id, modificationCounter, number, state) VALUES (101, 1, 1, 2);
INSERT INTO RESTAURANTTABLE (id, modificationCounter, number, state) VALUES (102, 1, 2, 0);
INSERT INTO RESTAURANTTABLE (id, modificationCounter, number, state) VALUES (103, 1, 3, 0);
INSERT INTO RESTAURANTTABLE (id, modificationCounter, number, state) VALUES (104, 1, 4, 0);
INSERT INTO RESTAURANTTABLE (id, modificationCounter, number, state) VALUES (105, 1, 5, 0);

INSERT INTO PRODUCT (id, modificationCounter, dType, description) VALUES (1, 1, 'Meal', 'Schnitzel');
INSERT INTO PRODUCT (id, modificationCounter, dType, description) VALUES (2, 1, 'Meal', 'Goulasch');
INSERT INTO PRODUCT (id, modificationCounter, dType, description) VALUES (3, 1, 'Meal', 'Pfifferlinge');
INSERT INTO PRODUCT (id, modificationCounter, dType, description) VALUES (4, 1, 'Meal', 'Salat');

INSERT INTO PRODUCT (id, modificationCounter, dType, description) VALUES (5, 1, 'SideDish', 'Pommes');
INSERT INTO PRODUCT (id, modificationCounter, dType, description) VALUES (6, 1, 'SideDish', 'Reis');
INSERT INTO PRODUCT (id, modificationCounter, dType, description) VALUES (7, 1, 'SideDish', 'Brot');
INSERT INTO PRODUCT (id, modificationCounter, dType, description) VALUES (8, 1, 'SideDish', 'Knödel');

INSERT INTO PRODUCT (id, modificationCounter, alcoholic, dType, description) VALUES (9, 1, false, 'Drink', 'Wasser');
INSERT INTO PRODUCT (id, modificationCounter, alcoholic, dType, description) VALUES (10, 1, false, 'Drink', 'Cola');
INSERT INTO PRODUCT (id, modificationCounter, alcoholic, dType, description) VALUES (11, 1, false, 'Drink', 'Bier');
INSERT INTO PRODUCT (id, modificationCounter, alcoholic, dType, description) VALUES (12, 1, false, 'Drink', 'Wein / Apfelwein');

INSERT INTO OFFER (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (1, 1, 0, 6.99, 1, 7, 10, 'Schnitzel-Menü', 'Description of Schnitzel-Menü');
INSERT INTO OFFER (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (2, 1, 0, 7.99, 2, 8, 11, 'Goulasch-Menü', 'Description of Goulasch-Menü');
INSERT INTO OFFER (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (3, 1, 0, 8.99, 3, 10, 12, 'Pfifferlinge-Menü', 'Description of Pfifferlinge-Menü');
INSERT INTO OFFER (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (4, 1, 0, 5.99, 4, 9, 9, 'Salat-Menü', 'Description of Salat-Menü');
INSERT INTO OFFER (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (5, 1, 0, 1.20, null, null, 10, 'Cola', 'Description of Salat-Menü');

INSERT INTO RESTAURANTORDER (id, modificationCounter, tableId, state) VALUES (1, 1, 101, 1);

INSERT INTO ORDERPOSITION (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (1, 1, 1, 2, 2, 1, 6.99, 'Schnitzel-Menü', 'mit Ketschup');
INSERT INTO ORDERPOSITION (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (2, 1, 2, 2, 2, 1, 7.99, 'Goulasch-Menü', '');
INSERT INTO ORDERPOSITION (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (3, 1, 3, 2, 2,  1, 8.99, 'Pfifferlinge-Menü','');
INSERT INTO ORDERPOSITION (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (4, 1, 4, 2, 2, 1, 5.99, 'Salat-Menü', '');
INSERT INTO ORDERPOSITION (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (5, 1, 5, 2, 2, 1, 5.99, 'Cola', '');

INSERT INTO BILL (id, modificationCounter, payed, total, tip) VALUES (1, 1, true, 14.98, 1.3);
INSERT INTO BILLORDERPOSITION (billId, orderPositionsId) VALUES (1,1);
INSERT INTO BILLORDERPOSITION (billId, orderPositionsId) VALUES (1,2);

INSERT INTO BILL (id, modificationCounter, payed, total,tip) VALUES (2, 1, true, 14.98, 1.4);
INSERT INTO BILLORDERPOSITION (billId, orderPositionsId) VALUES (2,3);
INSERT INTO BILLORDERPOSITION (billId, orderPositionsId) VALUES (2,4);

INSERT INTO BILL (id, modificationCounter, payed, total,tip) VALUES (3, 1, true, 15.98, 1.5);
INSERT INTO BILLORDERPOSITION (billId, orderPositionsId) VALUES (2,3);
INSERT INTO BILLORDERPOSITION (billId, orderPositionsId) VALUES (2,4);
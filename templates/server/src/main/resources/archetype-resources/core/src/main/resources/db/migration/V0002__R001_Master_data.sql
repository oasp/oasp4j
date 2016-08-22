INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (101, 1, 1, 2);
INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (102, 1, 2, 0);
INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (103, 1, 3, 0);
INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (104, 1, 4, 0);
INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (105, 1, 5, 0);

INSERT INTO Product (id, modificationCounter, dType, description) VALUES (1, 1, 'Meal', 'Schnitzel');
INSERT INTO Product (id, modificationCounter, dType, description) VALUES (2, 1, 'Meal', 'Goulasch');
INSERT INTO Product (id, modificationCounter, dType, description) VALUES (3, 1, 'Meal', 'Pfifferlinge');
INSERT INTO Product (id, modificationCounter, dType, description) VALUES (4, 1, 'Meal', 'Salat');
INSERT INTO Product (id, modificationCounter, dType, description) VALUES (5, 1, 'Meal', 'Pizza');
INSERT INTO Product (id, modificationCounter, dType, description) VALUES (6, 1, 'Meal', 'Flammkuchen');

INSERT INTO Product (id, modificationCounter, dType, description) VALUES (7, 1, 'SideDish', 'Pommes');
INSERT INTO Product (id, modificationCounter, dType, description) VALUES (8, 1, 'SideDish', 'Reis');
INSERT INTO Product (id, modificationCounter, dType, description) VALUES (9, 1, 'SideDish', 'Brot');
INSERT INTO Product (id, modificationCounter, dType, description) VALUES (10, 1, 'SideDish', 'Knödel');

INSERT INTO Product (id, modificationCounter, alcoholic, dType, description) VALUES (11, 1, false, 'Drink', 'Wasser');
INSERT INTO Product (id, modificationCounter, alcoholic, dType, description) VALUES (12, 1, false, 'Drink', 'Cola');
INSERT INTO Product (id, modificationCounter, alcoholic, dType, description) VALUES (13, 1, false, 'Drink', 'Bier');
INSERT INTO Product (id, modificationCounter, alcoholic, dType, description) VALUES (14, 1, false, 'Drink', 'Wein / Apfelwein');

INSERT INTO Offer (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (1, 1, 0, 6.99, 1, 7, 12, 'Schnitzel-Menü', 'Description of Schnitzel-Menü');
INSERT INTO Offer (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (2, 1, 0, 7.99, 2, 8, 13, 'Goulasch-Menü', 'Description of Goulasch-Menü');
INSERT INTO Offer (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (3, 1, 0, 8.99, 3, 10, 14, 'Pfifferlinge-Menü', 'Description of Pfifferlinge-Menü');
INSERT INTO Offer (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (4, 1, 0, 5.99, 4, 9, 11, 'Salat-Menü', 'Description of Salat-Menü');
INSERT INTO Offer (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (5, 1, 0, 1.20, null, null, 12, 'Cola', 'Description of Salat-Menü');
INSERT INTO Offer (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (6, 1, 0, 6.23, 5, null, 12, 'Pizza-Menü', 'Description of Pizza-Menü');
INSERT INTO Offer (id, modificationCounter, state, price, mealId, sideDishId, drinkId, name, description) VALUES (7, 1, 0, 5.99, 6, null, 12, 'Flammkuchen-Menü', 'Description of Flammkuchen-Menü');


INSERT INTO RestaurantOrder (id, modificationCounter, tableId, state) VALUES (1, 1, 101, 1);

INSERT INTO OrderPosition (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (1, 1, 1, 2, 2, 1, 6.99, 'Schnitzel-Menü', 'mit Ketschup');
INSERT INTO OrderPosition (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (2, 1, 2, 2, 2, 1, 7.99, 'Goulasch-Menü', '');
INSERT INTO OrderPosition (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (3, 1, 3, 2, 2,  1, 8.99, 'Pfifferlinge-Menü','');
INSERT INTO OrderPosition (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (4, 1, 4, 2, 2, 1, 5.99, 'Salat-Menü', '');
INSERT INTO OrderPosition (id, modificationCounter, offerId, state, drinkState, orderId, price, offerName, comment) VALUES (5, 1, 5, 2, 2, 1, 5.99, 'Cola', '');

INSERT INTO Bill (id, modificationCounter, payed, total, tip) VALUES (1, 1, true, 14.98, 1.3);
INSERT INTO BillOrderPosition (billId, orderPositionsId) VALUES (1,1);
INSERT INTO BillOrderPosition (billId, orderPositionsId) VALUES (1,2);

INSERT INTO Bill (id, modificationCounter, payed, total,tip) VALUES (2, 1, true, 14.98, 1.4);
INSERT INTO BillOrderPosition (billId, orderPositionsId) VALUES (2,3);
INSERT INTO BillOrderPosition (billId, orderPositionsId) VALUES (2,4);


INSERT INTO StaffMember (id, modificationCounter, role, login, firstname, lastname) VALUES (0, 0, 3, 'chief', 'Charly', 'Chief');
INSERT INTO StaffMember (id, modificationCounter, role, login, firstname, lastname) VALUES (1, 0, 0, 'cook', 'Carl', 'Cook');
INSERT INTO StaffMember (id, modificationCounter, role, login, firstname, lastname) VALUES (2, 0, 1, 'waiter', 'Willy', 'Waiter');
INSERT INTO StaffMember (id, modificationCounter, role, login, firstname, lastname) VALUES (3, 0, 2, 'barkeeper', 'Bianca', 'Barkeeper');

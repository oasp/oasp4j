INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (101, 1, 1, 2);
INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (102, 1, 2, 0);
INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (103, 1, 3, 0);
INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (104, 1, 4, 0);
INSERT INTO RestaurantTable (id, modificationCounter, number, state) VALUES (105, 1, 5, 0);

INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (1, 1, 'Meal', 'Schnitzel');
INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (2, 1, 'Meal', 'Goulasch');
INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (3, 1, 'Meal', 'Pfifferlinge');
INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (4, 1, 'Meal', 'Salat');
INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (5, 1, 'Meal', 'Pizza');
INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (6, 1, 'Meal', 'Flammkuchen');

INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (7, 1, 'SideDish', 'Pommes');
INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (8, 1, 'SideDish', 'Reis');
INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (9, 1, 'SideDish', 'Brot');
INSERT INTO Product (id, modificationCounter, dtype, description) VALUES (10, 1, 'SideDish', 'Knödel');

INSERT INTO Product (id, modificationCounter, dtype, description, alcoholic) VALUES (11, 1, 'Drink', 'Wasser', false);
INSERT INTO Product (id, modificationCounter, dtype, description, alcoholic) VALUES (12, 1, 'Drink', 'Cola', false);
INSERT INTO Product (id, modificationCounter, dtype, description, alcoholic) VALUES (13, 1, 'Drink', 'Bier', false);
INSERT INTO Product (id, modificationCounter, dtype, description, alcoholic) VALUES (14, 1, 'Drink', 'Wein / Apfelwein', false);

INSERT INTO Offer (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (1, 1, 'Schnitzel-Menü', 'Description of Schnitzel-Menü', 0, 1, 7, 12, 6.99);
INSERT INTO Offer (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (2, 1, 'Goulasch-Menü', 'Description of Goulasch-Menü', 0, 2, 8, 13, 7.99);
INSERT INTO Offer (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (3, 1, 'Pfifferlinge-Menü', 'Description of Pfifferlinge-Menü', 0, 3, 10, 14, 8.99);
INSERT INTO Offer (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (4, 1, 'Salat-Menü', 'Description of Salat-Menü', 0, 4, 9, 11, 5.99);
INSERT INTO Offer (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (5, 1, 'Cola', 'Description of Salat-Menü', 0, null, null, 12, 1.20);
INSERT INTO Offer (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (6, 1, 'Pizza-Menü', 'Description of Pizza-Menü', 0, 5, null, 12, 6.23);
INSERT INTO Offer (id, modificationCounter, name, description, state, meal_id, sidedish_id, drink_id, price) VALUES (7, 1, 'Flammkuchen-Menü', 'Description of Flammkuchen-Menü', 0, 6, null, 12, 5.99);


INSERT INTO RestaurantOrder (id, modificationCounter, table_id, state) VALUES (1, 1, 101, 1);

INSERT INTO OrderPosition (id, modificationCounter, offer_id, offername, comment, state, drinkState, order_id, price) VALUES (1, 1, 1, 'Schnitzel-Menü', 'mit Ketschup', 2, 2, 1, 6.99);
INSERT INTO OrderPosition (id, modificationCounter, offer_id, offername, comment, state, drinkState, order_id, price) VALUES (2, 1, 2, 'Goulasch-Menü', '', 2, 2, 1, 7.99);
INSERT INTO OrderPosition (id, modificationCounter, offer_id, offername, comment, state, drinkState, order_id, price) VALUES (3, 1, 3, 'Pfifferlinge-Menü','', 2, 2,  1, 8.99);
INSERT INTO OrderPosition (id, modificationCounter, offer_id, offername, comment, state, drinkState, order_id, price) VALUES (4, 1, 4, 'Salat-Menü', '', 2, 2, 1, 5.99);
INSERT INTO OrderPosition (id, modificationCounter, offer_id, offername, comment, state, drinkState, order_id, price) VALUES (5, 1, 5, 'Cola', '', 2, 2, 1, 5.99);

INSERT INTO Bill (id, modificationCounter, payed, total, tip) VALUES (1, 1, true, 14.98, 1.3);
INSERT INTO Bill_OrderPosition (bill_id, orderpositions_id) VALUES (1,1);
INSERT INTO Bill_OrderPosition (bill_id, orderpositions_id) VALUES (1,2);

INSERT INTO Bill (id, modificationCounter, payed, total,tip) VALUES (2, 1, true, 14.98, 1.4);
INSERT INTO Bill_OrderPosition (bill_id, orderpositions_id) VALUES (2,3);
INSERT INTO Bill_OrderPosition (bill_id, orderpositions_id) VALUES (2,4);

INSERT INTO StaffMember (id, login, role, firstname, lastname, modificationCounter) VALUES (0, 'chief', 3, 'Charly', 'Chief', 0);
INSERT INTO StaffMember (id, login, role, firstname, lastname, modificationCounter) VALUES (1, 'cook', 0, 'Carl', 'Cook', 0);
INSERT INTO StaffMember (id, login, role, firstname, lastname, modificationCounter) VALUES (2, 'waiter', 1, 'Willy', 'Waiter', 0);
INSERT INTO StaffMember (id, login, role, firstname, lastname, modificationCounter) VALUES (3, 'barkeeper', 2, 'Bianca', 'Barkeeper', 0);

SET FOREIGN_KEY_CHECKS=0;

DELETE FROM RestaurantTable;
DELETE FROM PRODUCT;
DELETE FROM OFFER;
DELETE FROM TABLEORDER;
DELETE FROM ORDERPOSITION;
DELETE FROM BILL;
DELETE FROM STAFFMEMBER;

INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (0, 'chief', 0, 'Charly', 'Chief', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (1, 'cook', 1, 'Carl', 'Cook', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (2, 'waiter', 2, 'Willy', 'Waiter', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (3, 'barkeeper', 3, 'Bianca', 'Barkeeper', 0);

SET FOREIGN_KEY_CHECKS=1;
SET FOREIGN_KEY_CHECKS=0;

DELETE FROM RESTAURANTTABLE;
DELETE FROM RESTAURANTORDER;
DELETE FROM PRODUCT;
DELETE FROM OFFER;
DELETE FROM ORDERPOSITION;
DELETE FROM BILL;
DELETE FROM STAFFMEMBER;

INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (0, 'chief', 3, 'Charly', 'Chief', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (1, 'cook', 0, 'Carl', 'Cook', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (2, 'waiter', 1, 'Willy', 'Waiter', 0);
INSERT INTO STAFFMEMBER (id, login, role, firstname, lastname, modificationCounter) VALUES (3, 'barkeeper', 2, 'Bianca', 'Barkeeper', 0);

SET FOREIGN_KEY_CHECKS=1;

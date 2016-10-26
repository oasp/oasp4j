-- Rename this file to V0001__R001_Create_schema_MYSQL.sql.mysql if the database used is not MariaDB 10.0.27

-- This is the SQL script for setting up the DDL for the h2 database
-- In a typical project you would only distinguish between main and test for flyway SQLs
-- However, in this sample application we provde support for multiple databases in parallel
-- You can simply choose the DB of your choice by setting spring.profiles.active=XXX in config/application.properties
-- Assuming that the preconfigured user exists with according credentials using the included SQLs

USE RESTAURANT;

SET FOREIGN_KEY_CHECKS = 0;

-- *** Staffmemeber ***
CREATE TABLE STAFFMEMBER(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    modificationCounter INT NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    login VARCHAR(255),
    role INTEGER
);

ALTER TABLE STAFFMEMBER ADD CONSTRAINT UC_STAFFMEMBER_LOGIN UNIQUE(login);

-- *** Product ***
CREATE TABLE PRODUCT(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dtype VARCHAR(31) NOT NULL,
    modificationCounter INT NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    alcoholic BIT,
    pictureId BIGINT
);

CREATE TABLE PRODUCT_AUD(
    revtype TINYINT,
    description VARCHAR(255),
    name VARCHAR(255),
    pictureId BIGINT,
    alcoholic BIT,
    dtype VARCHAR(31) NOT NULL,
    id BIGINT NOT NULL,
    rev BIGINT NOT NULL
);

-- *** Offer ***
CREATE TABLE OFFER(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    modificationCounter INT NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    price DECIMAL(19, 2),
    number BIGINT,
    state INT,
    drink_id BIGINT,
    meal_id BIGINT,
    sideDish_id BIGINT
);

ALTER TABLE OFFER ADD CONSTRAINT UC_OFFER_NAME UNIQUE(name);
ALTER TABLE OFFER ADD CONSTRAINT UC_OFFER_NUMBER UNIQUE(number);

ALTER TABLE OFFER ADD CONSTRAINT FK_OFFER2SIDEDISH FOREIGN KEY(sideDish_id) REFERENCES PRODUCT(id);
ALTER TABLE OFFER ADD CONSTRAINT FK_OFFER2MEAL FOREIGN KEY(meal_id) REFERENCES PRODUCT(id);
ALTER TABLE OFFER ADD CONSTRAINT FK_OFFER2DRINK FOREIGN KEY(drink_id) REFERENCES PRODUCT(id);

-- *** RestaurantTable (Table is a reserved keyword in Oracle) ***
CREATE TABLE RESTAURANTTABLE(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    modificationCounter INT NOT NULL,
    number BIGINT NOT NULL CHECK (NUMBER >= 0),
    state INT,
    waiter_id BIGINT
);

ALTER TABLE RESTAURANTTABLE ADD CONSTRAINT UC_TABLE_NUMBER UNIQUE(number);

-- *** RestaurantOrder (Order is a reserved keyword in Oracle) ***
CREATE TABLE RESTAURANTORDER(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    modificationCounter INTEGER NOT NULL,
    state INT,
    table_id BIGINT NOT NULL
);

-- *** OrderPosition ***
CREATE TABLE ORDERPOSITION(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    modificationCounter INT NOT NULL,
    comment VARCHAR(255),
    cook_id BIGINT,
    offer_id BIGINT,
    offerName VARCHAR(255),
    price DECIMAL(19, 2),
    state INT,
    drinkState INT,
    order_id BIGINT
);

ALTER TABLE ORDERPOSITION ADD CONSTRAINT FK_ORDPOS2ORDER FOREIGN KEY(order_id) REFERENCES RESTAURANTORDER(id);
ALTER TABLE ORDERPOSITION ADD CONSTRAINT FK_ORDPOS2COOK FOREIGN KEY(cook_id) REFERENCES STAFFMEMBER(id);

-- *** Bill ***
CREATE TABLE BILL(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    modificationCounter INT NOT NULL,
    payed BIT NOT NULL,
    tip DECIMAL(19, 2),
    total DECIMAL(19, 2)
);

CREATE TABLE BILL_ORDERPOSITION(
    bill_id BIGINT NOT NULL,
    orderpositions_id BIGINT NOT NULL
);

ALTER TABLE BILL_ORDERPOSITION ADD CONSTRAINT FK_BILLORDPOS2BILL FOREIGN KEY(bill_id) REFERENCES BILL(id);
ALTER TABLE BILL_ORDERPOSITION ADD CONSTRAINT FK_BILLORDPOS2ORDPOS FOREIGN KEY(orderPositions_ID) REFERENCES ORDERPOSITION(id);

-- *** BinaryObject (BLOBs) ***
CREATE TABLE BINARYOBJECT (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  modificationCounter INT NOT NULL,
  data LONGBLOB,
  size BIGINT NOT NULL,
  mimetype VARCHAR(255)
);

-- *** RevInfo (Commit log for envers audit trail) ***
CREATE TABLE REVINFO(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    timestamp BIGINT NOT NULL,
    user VARCHAR(255)
);

SET FOREIGN_KEY_CHECKS = 1;
-- This is the SQL script for setting up the DDL for the PostGres database
-- In a typical project you would only distinguish between main and test for flyway SQLs
-- However, in this sample application we provde support for multiple databases in parallel
-- You can simply choose the DB of your choice by setting spring.profiles.active=XXX in config/application.properties
-- Assuming that the preconfigured user exists with according credentials using the included SQLs

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1000000;

-- *** Staffmemeber ***
CREATE TABLE STAFFMEMBER(
    id BIGSERIAL NOT NULL,
    modificationCounter INTEGER NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    login VARCHAR(255),
    role INTEGER
);
ALTER TABLE STAFFMEMBER ADD CONSTRAINT PK_STAFFMEMEBER PRIMARY KEY(id);
ALTER TABLE STAFFMEMBER ADD CONSTRAINT UC_STAFFMEMBER_LOGIN UNIQUE(login);

-- *** Product ***
CREATE TABLE PRODUCT(
    dtype VARCHAR(31) NOT NULL,
    id BIGSERIAL NOT NULL,
    modificationCounter INTEGER NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    alcoholic BOOLEAN,
    pictureId BIGINT
);
ALTER TABLE PRODUCT ADD CONSTRAINT PK_PRODUCT PRIMARY KEY(id);
CREATE TABLE PRODUCT_AUD(
    revtype smallint,
    description VARCHAR(255),
    name VARCHAR(255),
    pictureId BIGINT,
    alcoholic BOOLEAN,
    dtype VARCHAR(31) NOT NULL,
    id BIGINT NOT NULL,
    rev BIGINT NOT NULL
);

-- *** Offer ***
CREATE TABLE OFFER(
    id BIGSERIAL NOT NULL,
    modificationCounter INTEGER NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    price DECIMAL(19, 2),
    number BIGINT,
    state INTEGER,
    drink_id BIGINT,
    meal_id BIGINT,
    sideDish_id BIGINT
);

ALTER TABLE OFFER ADD CONSTRAINT PK_OFFER PRIMARY KEY(id);
ALTER TABLE OFFER ADD CONSTRAINT UC_OFFER_NAME UNIQUE(name);
ALTER TABLE OFFER ADD CONSTRAINT UC_OFFER_NUMBER UNIQUE(number);

ALTER TABLE OFFER ADD CONSTRAINT FK_OFFER2SIDEDISH FOREIGN KEY(sideDish_id) REFERENCES PRODUCT(id) ;
ALTER TABLE OFFER ADD CONSTRAINT FK_OFFER2MEAL FOREIGN KEY(meal_id) REFERENCES PRODUCT(id) ;
ALTER TABLE OFFER ADD CONSTRAINT FK_OFFER2DRINK FOREIGN KEY(drink_id) REFERENCES PRODUCT(id) ;

-- *** RestaurantTable (Table is a reserved keyword in Oracle) ***
CREATE TABLE RESTAURANTTABLE(
    id BIGSERIAL NOT NULL,
    modificationCounter INTEGER NOT NULL,
    number BIGINT NOT NULL CHECK (NUMBER >= 0),
    state INTEGER,
    waiter_id BIGINT
);
ALTER TABLE RESTAURANTTABLE ADD CONSTRAINT PK_RESTAURANTTABLE PRIMARY KEY(id);
ALTER TABLE RESTAURANTTABLE ADD CONSTRAINT UC_TABLE_NUMBER UNIQUE(number);

-- *** RestaurantOrder (Order is a reserved keyword in Oracle) ***
CREATE TABLE RESTAURANTORDER(
    id BIGSERIAL NOT NULL,
    modificationCounter INTEGER NOT NULL,
    state INTEGER,
    table_id BIGINT NOT NULL
);
ALTER TABLE RESTAURANTORDER ADD CONSTRAINT PK_RESTAURANTORDER PRIMARY KEY(id);

-- *** OrderPosition ***
CREATE TABLE ORDERPOSITION(
    id BIGSERIAL NOT NULL,
    modificationCounter INTEGER NOT NULL,
    comment VARCHAR(255),
    cook_id BIGINT,
    offer_id BIGINT,
    offerName VARCHAR(255),
    price DECIMAL(19, 2),
    state INTEGER,
    drinkState INTEGER,
    order_id BIGINT
);

ALTER TABLE ORDERPOSITION ADD CONSTRAINT PK_ORDERPOSITON PRIMARY KEY(id);
ALTER TABLE ORDERPOSITION ADD CONSTRAINT FK_ORDPOS2ORDER FOREIGN KEY(order_id) REFERENCES RESTAURANTORDER(id) ;
ALTER TABLE ORDERPOSITION ADD CONSTRAINT FK_ORDPOS2COOK FOREIGN KEY(cook_id) REFERENCES STAFFMEMBER(id) ;

-- *** Bill ***
CREATE TABLE BILL(
    id BIGSERIAL NOT NULL,
    modificationCounter INTEGER NOT NULL,
    payed BOOLEAN NOT NULL,
    tip DECIMAL(19, 2),
    total DECIMAL(19, 2)
);
ALTER TABLE BILL ADD CONSTRAINT PK_BILL PRIMARY KEY(id);
CREATE TABLE BILL_ORDERPOSITION(
    bill_id BIGSERIAL NOT NULL,
    orderpositions_id BIGINT NOT NULL
);

ALTER TABLE BILL_ORDERPOSITION ADD CONSTRAINT FK_BILLORDPOS2BILL FOREIGN KEY(bill_id) REFERENCES BILL(id) ;
ALTER TABLE BILL_ORDERPOSITION ADD CONSTRAINT FK_BILLORDPOS2ORDPOS FOREIGN KEY(orderPositions_ID) REFERENCES ORDERPOSITION(id) ;

-- *** BinaryObject (BLOBs) ***
CREATE TABLE BINARYOBJECT (
  id BIGSERIAL NOT NULL,
  modificationCounter INTEGER NOT NULL,
  data BYTEA,
  size BIGINT NOT NULL,
  mimetype VARCHAR(255),
  PRIMARY KEY (ID)
);

-- *** RevInfo (Commit log for envers audit trail) ***
CREATE TABLE REVINFO(
    id BIGINT NOT NULL,
    timestamp BIGINT NOT NULL,
    "user" VARCHAR(255)
);
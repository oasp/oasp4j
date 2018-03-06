-- *** BinaryObject (BLOBs) ***
CREATE TABLE BinaryObject (
  id                  NUMBER(19),
  modificationCounter NUMBER(10, 0) NOT NULL,
  "data"              BLOB,
  "size"              NUMBER(10, 0) NOT NULL,
  mimeType            VARCHAR(255),
  PRIMARY KEY (ID)
);

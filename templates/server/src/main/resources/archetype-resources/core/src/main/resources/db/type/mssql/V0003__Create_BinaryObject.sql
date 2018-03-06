-- *** BinaryObject (BLOBs) ***
CREATE TABLE BINARYOBJECT (
  id                  BIGINT NOT NULL IDENTITY(10,1),
  modificationCounter INTEGER NOT NULL,
  data                varbinary(max),
  size                BIGINT NOT NULL,
  mimeType            VARCHAR(255),
  PRIMARY KEY (ID)
);

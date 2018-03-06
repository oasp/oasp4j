-- *** BinaryObject (BLOBs) ***
CREATE TABLE BINARYOBJECT (
  id                  BIGSERIAL NOT NULL,
  modificationCounter INTEGER NOT NULL,
  data                BYTEA,
  size                BIGINT NOT NULL,
  mimeType            VARCHAR(255),
  PRIMARY KEY (ID)
);

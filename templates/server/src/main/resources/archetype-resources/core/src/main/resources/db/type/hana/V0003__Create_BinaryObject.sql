-- *** BinaryObject (BLOBs) ***
CREATE TABLE BinaryObject (
  id                  BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  modificationCounter INTEGER NOT NULL,
  content             CLOB,
  filesize            BIGINT NOT NULL,
  mimeType            VARCHAR(255),
  CONSTRAINT PK_BinaryObject_id PRIMARY KEY(ID)
);

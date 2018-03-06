-- *** BinaryObject (BLOBs) ***
CREATE TABLE BINARYOBJECT (
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  modificationCounter INT NOT NULL,
  data                LONGBLOB,
  size                BIGINT NOT NULL,
  mimeType            VARCHAR(255)
);

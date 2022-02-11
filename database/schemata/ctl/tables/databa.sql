--------------------------------------------------------------------------------------------------------------------------------
-- Script:      databa.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Table CTL.DATABA (Database)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ctl.databa
(
  db_id                          INTEGER NOT NULL,
  db_name                        VARCHAR(18) NOT NULL,
  db_seed                        VARCHAR(10) NOT NULL,
  db_type                        VARCHAR(20) NOT NULL,
  suspend_from_timestamp         TIMESTAMP(0),
  suspend_to_timestamp           TIMESTAMP(0),
  suspend_type                   VARCHAR(20),
  created_timestamp              TIMESTAMP(0) NOT NULL,
  updated_timestamp              TIMESTAMP(0) NOT NULL
);

ALTER TABLE ctl.databa
ADD CONSTRAINT databa_pk PRIMARY KEY (db_id);

ALTER TABLE ctl.databa
ADD CONSTRAINT databa_uk1 UNIQUE (db_name, db_seed, db_type);

ALTER TABLE ctl.databa
ADD CONSTRAINT databa_cc01 CHECK (db_type IN ('DB2LUW'));

ALTER TABLE ctl.databa
ADD CONSTRAINT databa_cc02 CHECK (suspend_from_timestamp < suspend_to_timestamp);

ALTER TABLE ctl.databa
ADD CONSTRAINT databa_cc03 CHECK (suspend_type IN ('UPDATES_AND_READS', 'UPDATES_ONLY'));

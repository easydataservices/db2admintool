--------------------------------------------------------------------------------------------------------------------------------
-- Script:      databa.sql
-- Licence:     GNU General Public License v3.0
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
  db_type                        VARCHAR(20) NOT NULL
);

ALTER TABLE ctl.databa
ADD CONSTRAINT databa_pk PRIMARY KEY (db_id);

ALTER TABLE ctl.databa
ADD CONSTRAINT databa_uk1 UNIQUE (db_name, db_seed, db_type);

ALTER TABLE ctl.databa
ADD CONSTRAINT databa_cc01 CHECK (db_type IN ('DB2LUW'));

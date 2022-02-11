--------------------------------------------------------------------------------------------------------------------------------
-- Script:      dbagnt.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Table CTL.DBAGNT (Database Agent)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ctl.dbagnt
(
  agent_id                       INTEGER NOT NULL,
  db_id                          INTEGER NOT NULL,
  user_name                      VARCHAR(128) NOT NULL,
  host_name                      VARCHAR(255) NOT NULL,
  created_timestamp              TIMESTAMP(0) NOT NULL,
  updated_timestamp              TIMESTAMP(0) NOT NULL
);

ALTER TABLE ctl.dbagnt
ADD CONSTRAINT dbagnt_pk PRIMARY KEY (agent_id);

ALTER TABLE ctl.dbagnt
ADD CONSTRAINT dbagnt_uk1 UNIQUE (db_id, agent_id);

ALTER TABLE ctl.dbagnt
ADD CONSTRAINT dbagnt_uk2 UNIQUE (db_id, user_name, host_name);

ALTER TABLE ctl.dbagnt
ADD CONSTRAINT dbagnt_databa_fk1 FOREIGN KEY (db_id) REFERENCES ctl.databa;

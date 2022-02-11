--------------------------------------------------------------------------------------------------------------------------------
-- Script:      dbrole.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Table CTL.DBROLE (Database Role)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ctl.dbrole
(
  db_id                          INTEGER NOT NULL,
  role                           VARCHAR(20) NOT NULL,
  agent_id                       INTEGER NOT NULL,
  auth_token                     VARCHAR(32) NOT NULL,
  auth_timestamp                 TIMESTAMP(0) NOT NULL,
  is_granted                     BOOLEAN NOT NULL DEFAULT FALSE,
  created_timestamp              TIMESTAMP(0) NOT NULL,
  updated_timestamp              TIMESTAMP(0) NOT NULL
);

ALTER TABLE ctl.dbrole
ADD CONSTRAINT dbrole_pk PRIMARY KEY (db_id, role, agent_id);

ALTER TABLE ctl.dbrole
ADD CONSTRAINT dbrole_uk1 UNIQUE (auth_token);

ALTER TABLE ctl.dbrole
ADD CONSTRAINT dbrole_dbagnt_fk1 FOREIGN KEY (db_id, agent_id) REFERENCES ctl.dbagnt(db_id, agent_id);

ALTER TABLE ctl.dbrole
ADD CONSTRAINT dbrole_cc01 CHECK (role IN ('REGISTRAR', 'MONITOR'));

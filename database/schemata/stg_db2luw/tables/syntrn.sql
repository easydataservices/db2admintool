--------------------------------------------------------------------------------------------------------------------------------
-- Script:      syntrn.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Table STG_DB2LUW.SYNTRN (Synthetic Transaction snapshot)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE stg_db2luw.syntrn
(
  db_id                          INTEGER NOT NULL,
  agent_id                       VARCHAR(30) NOT NULL,
  snapshot_timestamp             TIMESTAMP(0) NOT NULL,
  is_ok                          BOOLEAN NOT NULL
);

ALTER TABLE stg_db2luw.syntrn
ADD CONSTRAINT syntrn_pk PRIMARY KEY (db_id, agent_id, snapshot_timestamp);

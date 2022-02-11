--------------------------------------------------------------------------------------------------------------------------------
-- Script:      sndbcf.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Table STG_DB2LUW.SNDBCF (Database Configuration Snapshot for DB2 for LUW monitoring)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE stg_db2luw.sndbcf
(
  db_id                          INTEGER NOT NULL,
  agent_id                       VARCHAR(30) NOT NULL,
  snapshot_timestamp             TIMESTAMP(0) NOT NULL,
  db_member                      SMALLINT NOT NULL,
  parameter_name                 VARCHAR(32) NOT NULL,
  value                          VARCHAR(1024),
  value_flags                    INTEGER,
  deferred_value                 VARCHAR(1024),
  deferred_value_flags           INTEGER
);

ALTER TABLE stg_db2luw.sndbcf
ADD CONSTRAINT sndbcf_pk PRIMARY KEY (db_id, agent_id, snapshot_timestamp, db_member, parameter_name);

--------------------------------------------------------------------------------------------------------------------------------
-- Script:      sndbcf.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.23
--
-- Description: Table CTL_DB2LUW.SNDBCF
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ctl_db2luw.sndbcf
(
  db_id                          INTEGER NOT NULL,
  agent_id                       INTEGER NOT NULL,
  snapshot_timestamp             TIMESTAMP(0) NOT NULL,
  transform_timestamp            TIMESTAMP(0)
);

ALTER TABLE ctl_db2luw.sndbcf
ADD CONSTRAINT sndbcf_pk PRIMARY KEY (db_id, agent_id, snapshot_timestamp);

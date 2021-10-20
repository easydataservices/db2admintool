--------------------------------------------------------------------------------------------------------------------------------
-- Script:      sndbcf.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Table CLI_DB2LUW.SNDBCF (Database Configuration Snapshot for DB2 for LUW monitoring)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE cli_db2luw.sndbcf
(
  db_id                          INTEGER NOT NULL,
  snapshot_timestamp             TIMESTAMP(0) NOT NULL,
  db_partition_number            SMALLINT NOT NULL,
  db_member                      SMALLINT NOT NULL,
  parameter_name                 VARCHAR(32) NOT NULL,
  value                          VARCHAR(1024),
  value_flags                    VARCHAR(10),
  deferred_value                 VARCHAR(1024),
  deferred_value_flags           VARCHAR(10)
);

ALTER TABLE cli_db2luw.sndbcf
ADD CONSTRAINT sndbcf_pk PRIMARY KEY (db_id, snapshot_timestamp, db_partition_number, db_member, parameter_name);

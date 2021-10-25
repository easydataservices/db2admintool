--------------------------------------------------------------------------------------------------------------------------------
-- Script:      sndbcf.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.20
--
-- Description: Table REPO_DB2LUW.SNDBCF (Database Configuration Snapshot for DB2 for LUW monitoring)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE repo_db2luw.sndbcf
(
  db_id                          INTEGER NOT NULL,
  db_partition_number            SMALLINT NOT NULL,
  db_member                      SMALLINT NOT NULL,
  parameter_name                 VARCHAR(32) NOT NULL,
  from_timestamp                 TIMESTAMP(0) NOT NULL,
  to_timestamp                   TIMESTAMP(0) NOT NULL DEFAULT '9999-12-31-24.00.00',
  value                          VARCHAR(1024),
  value_flags                    VARCHAR(10),
  deferred_value                 VARCHAR(1024),
  deferred_value_flags           VARCHAR(10)
)
  ORGANIZE BY DIMENSIONS (db_id, db_partition_number, db_member);

CREATE UNIQUE INDEX repo_db2luw.sndbcf_pk ON repo_db2luw.sndbcf
(
  db_id, db_partition_number, db_member, parameter_name, from_timestamp
)
  INCLUDE (to_timestamp);

ALTER TABLE repo_db2luw.sndbcf
ADD CONSTRAINT sndbcf_pk PRIMARY KEY (db_id, parameter_name, db_partition_number, db_member, from_timestamp);

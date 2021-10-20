--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_sndbcf.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Module CLI_DB2LUW.SNDBCF
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE cli_db2luw.sndbcf;

ALTER MODULE cli_db2luw.sndbcf
PUBLISH TYPE snapshot_row AS ROW
(
  snapshot_timestamp TIMESTAMP(0),
  db_partition_number SMALLINT,
  db_member SMALLINT,
  parameter_name VARCHAR(32),
  value VARCHAR(1024),
  value_flags VARCHAR(10),
  deferred_value VARCHAR(1024),
  deferred_value_flags VARCHAR(10)
);

ALTER MODULE cli_db2luw.sndbcf
PUBLISH TYPE snapshot_array AS snapshot_row ARRAY[];

ALTER MODULE cli_db2luw.sndbcf
PUBLISH PROCEDURE save_snapshot
(
  p_db_name VARCHAR(18), p_db_seed VARCHAR(10), p_auth_token VARCHAR(32), p_snapshot_array snapshot_array
);

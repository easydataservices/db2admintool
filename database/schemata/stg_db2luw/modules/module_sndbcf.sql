--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_sndbcf.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Module STG_DB2LUW.SNDBCF
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE stg_db2luw.sndbcf;

ALTER MODULE stg_db2luw.sndbcf
PUBLISH TYPE parameter AS ROW
(
  db_name VARCHAR(18),
  db_member SMALLINT,
  db_status VARCHAR(20),
  parameter_name VARCHAR(32),
  value VARCHAR(1024),
  value_flags INTEGER,
  deferred_value VARCHAR(1024),
  deferred_value_flags INTEGER
);

ALTER MODULE stg_db2luw.sndbcf
PUBLISH TYPE parameter_array AS parameter ARRAY[];

ALTER MODULE stg_db2luw.sndbcf
PUBLISH PROCEDURE save_snapshot
(
  p_snapshot_timestamp TIMESTAMP(0), p_parameter_array parameter_array, p_auth_token VARCHAR(32)
);

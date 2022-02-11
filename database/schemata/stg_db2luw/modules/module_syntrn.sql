--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_sndbcf.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Module STG_DB2LUW.SYNTRN
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE stg_db2luw.syntrn;

ALTER MODULE stg_db2luw.syntrn
PUBLISH PROCEDURE save_snapshot
(
  p_snapshot_timestamp TIMESTAMP(0), p_is_transaction_ok BOOLEAN, p_auth_token VARCHAR(32)
);

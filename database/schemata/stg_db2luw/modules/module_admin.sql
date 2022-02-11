--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_admin.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Module STG_DB2LUW.ADMIN
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE stg_db2luw.admin;

ALTER MODULE stg_db2luw.admin
PUBLISH PROCEDURE register_db
(
  p_db_name VARCHAR(18), p_db_seed VARCHAR(10), OUT p_db_id INTEGER, OUT p_agent_id INTEGER, OUT p_auth_token VARCHAR(32)
);

ALTER MODULE stg_db2luw.admin
PUBLISH PROCEDURE request_db_role
(
  p_db_name VARCHAR(18), p_db_seed VARCHAR(10), p_role VARCHAR(20), OUT p_agent_id INTEGER, OUT p_auth_token VARCHAR(32)
);

ALTER MODULE stg_db2luw.admin
PUBLISH FUNCTION get_db_id(p_auth_token VARCHAR(32)) RETURNS INTEGER;

ALTER MODULE stg_db2luw.admin
PUBLISH FUNCTION get_agent_id(p_auth_token VARCHAR(32)) RETURNS INTEGER;

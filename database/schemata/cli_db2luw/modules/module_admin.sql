--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_admin.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Module CLI_DB2LUW.ADMIN
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE cli_db2luw.admin;

ALTER MODULE cli_db2luw.admin
PUBLISH PROCEDURE register_db
(
  p_db_name VARCHAR(18), p_db_seed VARCHAR(10), OUT p_db_id INTEGER, OUT p_agent_id INTEGER, OUT p_auth_token VARCHAR(32)
);

ALTER MODULE cli_db2luw.admin
PUBLISH PROCEDURE request_db_role
(
  p_db_name VARCHAR(18), p_db_seed VARCHAR(10), p_role VARCHAR(20), OUT p_agent_id INTEGER, OUT p_auth_token VARCHAR(32)
);

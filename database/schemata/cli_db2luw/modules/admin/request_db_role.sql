--------------------------------------------------------------------------------------------------------------------------------
-- Script:      request_db_role.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Stored procedure CLI_DB2LUW.ADMIN.REQUEST_DB_ROLE
--              Registers a new database.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE cli_db2luw.admin
ADD PROCEDURE request_db_role
(
  p_db_name VARCHAR(18), p_db_seed VARCHAR(10), p_role VARCHAR(20), OUT p_agent_id INTEGER, OUT p_auth_token VARCHAR(32)
)
BEGIN
  DECLARE v_db_id INTEGER;

  -- If database not registered then return error.
  SET v_db_id =
    (
      SELECT db_id FROM ctl.databa WHERE db_name = p_db_name AND db_seed = p_db_seed AND db_type = 'DB2LUW' WITH CS
    );
  IF v_db_id IS NULL THEN
    SIGNAL SQLSTATE '72001' SET MESSAGE_TEXT = 'Database is not registered';
  END IF;

  -- Add the role (not granted) to the agent.
  CALL ctl.security.add_agent(v_db_id, SYSTEM_USER, CLIENT_HOST, CLIENT_IPADDR, p_agent_id);
  CALL ctl.security.set_role(v_db_id, p_role, p_agent_id, FALSE, p_auth_token);
END@

--------------------------------------------------------------------------------------------------------------------------------
-- Script:      register_db.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Stored procedure STG_DB2LUW.ADMIN.REGISTER_DB
--              Registers a new database.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE stg_db2luw.admin
ADD PROCEDURE register_db
(
  p_db_name VARCHAR(18), p_db_seed VARCHAR(10), OUT p_db_id INTEGER, OUT p_agent_id INTEGER, OUT p_auth_token VARCHAR(32)
)
BEGIN
  -- If database is already registered then early exit.
  SET p_db_id = ctl_db2luw.common.get_db_id(p_db_name, p_db_seed);
  IF p_db_id IS NOT NULL THEN
    RETURN;
  END IF;

  -- Add the new database.
  SET p_db_id = (SELECT COALESCE(MAX(db_id) + 1, 1) AS db_id FROM ctl.databa WITH RR);
  INSERT INTO ctl.databa(db_id, db_name, db_seed, db_type)
  VALUES (p_db_id, p_db_name, p_db_seed, 'DB2LUW');
    
  -- Add REGISTRAR role (granted) to the agent.
  CALL ctl.security.add_agent(p_db_id, SYSTEM_USER, CLIENT_HOST, p_agent_id);
  CALL ctl.security.set_role(p_db_id, 'REGISTRAR', p_agent_id, TRUE, p_auth_token);
END@

--------------------------------------------------------------------------------------------------------------------------------
-- Script:      set_role.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.14
--
-- Description: Stored procedure CTL.SECURITY.SET_ROLE
--              Sets (or unsets) the specified role for the specified agent.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.security
ADD PROCEDURE set_role
(
  p_db_id INTEGER, 
  p_role VARCHAR(20), 
  p_agent_id INTEGER,
  p_is_granted BOOLEAN,
  OUT p_auth_token VARCHAR(32)
)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_is_granted BOOLEAN;
  
  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;
  
  -- Look up existing role details (if any).
  SET (p_auth_token, v_is_granted) =
    (
      SELECT auth_token, is_granted FROM ctl.dbrole WHERE (db_id, role, agent_id) = (p_db_id, p_role, p_agent_id) WITH RR
    );

  -- Add or update the role (if required).
  IF p_auth_token IS NULL THEN
    SET p_auth_token = ctl.security.generate_auth_token();
    INSERT INTO ctl.dbrole(db_id, role, agent_id, auth_token, auth_timestamp, is_granted, created_timestamp, updated_timestamp)
    VALUES
      (p_db_id, p_role, p_agent_id, p_auth_token, v_utc, p_is_granted, v_utc, v_utc);
  ELSEIF p_is_granted != v_is_granted THEN
    UPDATE ctl.dbrole
    SET
      is_granted = p_is_granted,
      updated_timestamp = v_utc;
  END IF;
END@

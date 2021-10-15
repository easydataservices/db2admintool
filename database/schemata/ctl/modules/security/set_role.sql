--------------------------------------------------------------------------------------------------------------------------------
-- Script:      set_role.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.14
--
-- Description: Stored procedure CTI.SECURITY.SET_ROLE
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
  
  -- Get UTC for transaction.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;
  SET p_auth_token = ctl.security.generate_auth_token();

  -- Add or update the role.
  MERGE INTO ctl.dbrole AS t
  USING
    (VALUES (p_db_id, p_role, p_agent_id, p_is_granted)) AS s(db_id, role, agent_id, is_granted)
  ON
    t.db_id = s.db_id AND
    t.role = s.role AND
    t.agent_id = s.agent_id
  WHEN NOT MATCHED THEN
    INSERT (db_id, role, agent_id, auth_token, auth_timestamp, is_granted, created_timestamp, updated_timestamp)
    VALUES
      (p_db_id, p_role, p_agent_id, p_auth_token, v_utc, p_is_granted, v_utc, v_utc)
  WHEN MATCHED AND t.is_granted != s.is_granted THEN
    UPDATE
    SET
      is_granted = s.is_granted,
      updated_timestamp = v_utc
  ELSE IGNORE
  WITH RR;
END@

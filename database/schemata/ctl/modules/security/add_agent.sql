--------------------------------------------------------------------------------------------------------------------------------
-- Script:      add_agent.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.14
--
-- Description: Stored procedure CTL.SECURITY.ADD_AGENT
--              Creates a new agent.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.security
ADD PROCEDURE add_agent
(
  p_db_id INTEGER, 
  p_user_name VARCHAR(128), 
  p_host_name VARCHAR(255),
  OUT p_agent_id INTEGER
)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  
  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;

  -- If agent already exists then early out.
  SET p_agent_id =
    (
      SELECT
        agent_id
      FROM
        ctl.dbagnt
      WHERE
        db_id = p_db_id AND 
        user_name = p_user_name AND 
        host_name = p_host_name
      WITH CS
    );
  IF p_agent_id IS NOT NULL THEN
    RETURN;
  END IF;

  -- Add the new agent.
  SET p_agent_id = (SELECT COALESCE(MAX(agent_id) + 1, 1) AS agent_id FROM ctl.dbagnt WITH RR);
  INSERT INTO ctl.dbagnt(agent_id, db_id, user_name, host_name, created_timestamp, updated_timestamp)
  VALUES
    (p_agent_id, p_db_id, p_user_name, p_host_name, v_utc, v_utc);
END@

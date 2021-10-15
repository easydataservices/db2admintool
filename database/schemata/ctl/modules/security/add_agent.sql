--------------------------------------------------------------------------------------------------------------------------------
-- Script:      add_agent.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.14
--
-- Description: Stored procedure CTI.SECURITY.ADD_AGENT
--              Creates a new agent.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.security
ADD PROCEDURE add_agent
(
  p_db_id INTEGER, 
  p_user_name VARCHAR(128), 
  p_client_hostname VARCHAR(255),
  p_client_ip_address VARCHAR(128),
  OUT p_agent_id INTEGER
)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  
  -- Get UTC for transaction.
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
        user_name = UCASE(p_user_name) AND 
        client_hostname = UCASE(p_client_hostname) AND
        UCASE(p_client_ip_address) LIKE client_ip_address
      WITH CS
    );
  IF p_agent_id IS NOT NULL THEN
    RETURN;
  END IF;

  -- Add the new agent.
  SET p_agent_id = (SELECT COALESCE(MAX(p_agent_id) + 1, 1) AS p_agent_id FROM ctl.dbagnt WITH RR);
  INSERT INTO ctl.dbagnt(agent_id, db_id, user_name, client_hostname, client_ip_address, created_timestamp, updated_timestamp)
  VALUES
    (p_agent_id, p_db_id, UCASE(p_user_name), UCASE(p_client_hostname), UCASE(p_client_ip_address), v_utc, v_utc);
END@

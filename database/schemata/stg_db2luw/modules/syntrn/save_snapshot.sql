--------------------------------------------------------------------------------------------------------------------------------
-- Script:      save_snapshot.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.17
--
-- Description: Stored procedure STG_DB2LUW.SYNTRN.SAVE_SNAPSHOT
--              Persists synthetic transaction result to repository.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE stg_db2luw.syntrn
ADD PROCEDURE save_snapshot
(
  p_snapshot_timestamp TIMESTAMP(0), p_is_transaction_ok BOOLEAN, p_auth_token VARCHAR(32)
)
BEGIN ATOMIC
  DECLARE v_db_id INTEGER;
  DECLARE v_agent_id INTEGER;

  -- Check permissions.
  SET v_db_id = stg_db2luw.admin.get_db_id(p_auth_token);
  SET v_agent_id = stg_db2luw.admin.get_agent_id(p_auth_token);
  IF v_db_id IS NULL THEN
    SIGNAL SQLSTATE '72001' SET MESSAGE_TEXT = 'Database is not registered';
  END IF;
  IF v_agent_id IS NULL THEN
    SIGNAL SQLSTATE '72001' SET MESSAGE_TEXT = 'Agent is not registered';
  END IF;
  IF NOT ctl.security.is_role_granted(v_db_id, 'MONITOR', p_auth_token) THEN
    SIGNAL SQLSTATE '72002' SET MESSAGE_TEXT = 'Insufficient permissions';
  END IF;

  -- Insert snapshot row.
  INSERT INTO stg_db2luw.syntrn(db_id, agent_id, snapshot_timestamp, is_ok)
  VALUES
    (v_db_id, v_agent_id, p_snapshot_timestamp, p_is_transaction_ok);
    
  -- Insert new control row.
  INSERT INTO ctl_db2luw.syntrn(db_id, agent_id, snapshot_timestamp)
  VALUES
    (v_db_id, v_agent_id, p_snapshot_timestamp);
END@

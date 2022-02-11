--------------------------------------------------------------------------------------------------------------------------------
-- Script:      save_snapshot.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.17
--
-- Description: Stored procedure STG_DB2LUW.SNDBCF.SAVE_SNAPSHOT
--              Persists database configuration snapshot to repository.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE stg_db2luw.sndbcf
ADD PROCEDURE save_snapshot
(
  p_snapshot_timestamp TIMESTAMP(0), p_parameter_array parameter_array, p_auth_token VARCHAR(32)
)
BEGIN ATOMIC
  DECLARE v_db_id INTEGER;
  DECLARE v_agent_id INTEGER;
  DECLARE v_is_first_row BOOLEAN DEFAULT TRUE;

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

  -- Insert snapshot rows.
  FOR r AS
    SELECT * FROM UNNEST(p_parameter_array)
  DO
    -- Control row handling
    IF v_is_first_row THEN
      -- Check there is no control row for a later snapshot (sequence error).
      IF EXISTS 
      (
        SELECT * FROM ctl_db2luw.sndbcf
        WHERE
          db_id = v_db_id AND agent_id = v_agent_id AND snapshot_timestamp >= p_snapshot_timestamp WITH RR
      ) THEN
        SIGNAL SQLSTATE '72011' SET MESSAGE_TEXT = 'Snapshot out of sequence';
      END IF;

      -- Insert new control row.
      INSERT INTO ctl_db2luw.sndbcf(db_id, agent_id, snapshot_timestamp)
      VALUES
        (v_db_id, v_agent_id, p_snapshot_timestamp);
      SET v_is_first_row = FALSE;
    END IF;
    
    -- Insert snapshot rows into staging area.
    INSERT INTO stg_db2luw.sndbcf(db_id, agent_id, snapshot_timestamp, db_member, parameter_name, value, value_flags, deferred_value, deferred_value_flags)
    VALUES
      (v_db_id, v_agent_id, p_snapshot_timestamp, r.db_member, r.parameter_name, r.value, r.value_flags, r.deferred_value, r.deferred_value_flags);
  END FOR;
END@

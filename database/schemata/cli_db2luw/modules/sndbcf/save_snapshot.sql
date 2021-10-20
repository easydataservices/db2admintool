--------------------------------------------------------------------------------------------------------------------------------
-- Script:      save_snapshot.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.17
--
-- Description: Stored procedure CLI_DB2LUW.SNDBCF.SAVE_SNAPSHOT
--              Persists database configuration snapshot to repository.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE cli_db2luw.sndbcf
ADD PROCEDURE save_snapshot
(
  p_db_name VARCHAR(18), p_db_seed VARCHAR(10), p_auth_token VARCHAR(32), p_snapshot_array snapshot_array
)
BEGIN
  DECLARE v_db_id INTEGER;

  -- Check permissions.
  SET v_db_id = cli_db2luw.admin.get_db_id(p_db_name, p_db_seed);
  IF v_db_id IS NULL THEN
    SIGNAL SQLSTATE '72001' SET MESSAGE_TEXT = 'Database is not registered';
  END IF;
  IF NOT ctl.security.is_role_granted(v_db_id, 'MONITOR', p_auth_token) THEN
    SIGNAL SQLSTATE '72002' SET MESSAGE_TEXT = 'Insufficient permissions';
  END IF;

  -- Insert snapshot rows.
  FOR r AS
    SELECT * FROM UNNEST(p_snapshot_array)
  DO
    INSERT INTO cli_db2luw.sndbcf(db_id, snapshot_timestamp, db_partition_number, db_member, parameter_name, value, value_flags, deferred_value, deferred_value_flags)
    VALUES
      (1, r.snapshot_timestamp, r.db_partition_number, r.db_member, r.parameter_name, r.value, r.value_flags, r.deferred_value, r.deferred_value_flags);
  END FOR;
END@

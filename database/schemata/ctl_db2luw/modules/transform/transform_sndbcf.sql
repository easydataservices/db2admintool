--------------------------------------------------------------------------------------------------------------------------------
-- Script:      transform_sndbcf.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.23
--
-- Description: Stored procedure CTL_DB2LUW.SNDBCF.TRANSFORM_SNDBCF
--              Persists database configuration snapshot to repository.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl_db2luw.transform
ADD PROCEDURE transform_sndbcf()
BEGIN
  FOR c AS c1 INSENSITIVE CURSOR WITH HOLD FOR
    SELECT snapshot_timestamp, db_id FROM ctl_db2luw.sndbcf WHERE transform_timestamp IS NULL ORDER BY 1, 2 WITH RS
  DO
    -- Process deleted parameters.
    FOR r AS
      SELECT 
        t.*
      FROM
        repo_db2luw.sndbcf AS t
          LEFT OUTER JOIN
        (
          SELECT * FROM cli_db2luw.sndbcf WHERE db_id = c.db_id AND snapshot_timestamp = c.snapshot_timestamp
        ) AS n
          ON
            t.db_id = n.db_id AND
            t.db_partition_number = n.db_partition_number AND
            t.db_member = n.db_member AND
            t.parameter_name = n.parameter_name
      WHERE
        t.db_id = c.db_id AND
        t.to_timestamp = '9999-12-31-24.00.00' AND 
        n.db_id IS NULL
      WITH CS
    DO
      UPDATE repo_db2luw.sndbcf 
      SET 
        to_timestamp = c.snapshot_timestamp - 1 SECOND
      WHERE
        db_id = r.db_id AND
        db_partition_number = r.db_partition_number AND
        db_member = r.db_member AND
        parameter_name = r.parameter_name AND
        to_timestamp = '9999-12-31-24.00.00';
    END FOR;

    -- Process new and changed parameters.
    FOR r AS
      SELECT 
        n.*,
        CASE
          WHEN t.db_id IS NULL THEN NULL
          WHEN (t.value != n.value) OR (t.value IS NULL AND n.value IS NOT NULL) OR (t.value IS NOT NULL AND n.value IS NULL) THEN TRUE
          WHEN (t.value_flags != n.value_flags) OR (t.value_flags IS NULL AND n.value_flags IS NOT NULL) OR (t.value_flags IS NOT NULL AND n.value_flags IS NULL) THEN TRUE
          WHEN (t.deferred_value != n.deferred_value) OR (t.deferred_value IS NULL AND n.deferred_value IS NOT NULL) OR (t.deferred_value IS NOT NULL AND n.deferred_value IS NULL) THEN TRUE
          WHEN (t.deferred_value_flags != n.deferred_value_flags) OR (t.deferred_value_flags IS NULL AND n.deferred_value_flags IS NOT NULL) OR (t.deferred_value_flags IS NOT NULL AND n.deferred_value_flags IS NULL) THEN TRUE
          ELSE FALSE
        END AS is_changed
      FROM 
        cli_db2luw.sndbcf AS n
          LEFT OUTER JOIN
        (
          SELECT
            t.*
          FROM
            repo_db2luw.sndbcf AS t
          WHERE
            db_id = c.db_id AND to_timestamp = '9999-12-31-24.00.00'
        ) AS t
          ON
            n.db_id = t.db_id AND
            n.db_partition_number = t.db_partition_number AND
            n.db_member = t.db_member AND
            n.parameter_name = t.parameter_name AND
            n.snapshot_timestamp BETWEEN t.from_timestamp AND t.to_timestamp
      WHERE
        n.db_id = c.db_id AND n.snapshot_timestamp = c.snapshot_timestamp
      WITH CS
    DO
      IF r.is_changed THEN
        UPDATE repo_db2luw.sndbcf
        SET
          to_timestamp = r.snapshot_timestamp - 1 SECOND
        WHERE
          db_id = r.db_id AND
          db_partition_number = r.db_partition_number AND
          db_member = r.db_member AND
          parameter_name = r.parameter_name AND
          r.snapshot_timestamp BETWEEN from_timestamp AND to_timestamp;
      END IF;
      
      IF r.is_changed IS NULL THEN
        INSERT INTO repo_db2luw.sndbcf(db_id, db_partition_number, db_member, parameter_name, from_timestamp, value, value_flags, deferred_value, deferred_value_flags)
        VALUES
          (r.db_id, r.db_partition_number, r.db_member, r.parameter_name, r.snapshot_timestamp, r.value, r.value_flags, r.deferred_value, r.deferred_value_flags);
      END IF;
    END FOR;

    -- Delete staging data.
    DELETE FROM cli_db2luw.sndbcf 
    WHERE
      db_id = c.db_id AND snapshot_timestamp = c.snapshot_timestamp;

    -- Update control row.
    UPDATE ctl_db2luw.sndbcf 
    SET
      transform_timestamp = CURRENT_TIMESTAMP - CURRENT_TIMEZONE
    WHERE
      db_id = c.db_id AND snapshot_timestamp = c.snapshot_timestamp;
  END FOR;
  
  -- Commit the transformed snapshot.
  COMMIT;
END@

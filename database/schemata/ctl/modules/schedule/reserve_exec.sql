--------------------------------------------------------------------------------------------------------------------------------
-- Script:      reserve_exec.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.31
--
-- Description: Stored procedure CTL.SCHEDULE.RESERVE_EXEC
--              Attempts to reserve an execution of the specified job.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD PROCEDURE reserve_exec(p_db_id INTEGER, p_job_id INTEGER, p_exec_timestamp TIMESTAMP(0), OUT p_reserved_id INTEGER)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_permitted_exec_delay_seconds SMALLINT;
  
  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;

  -- Retrieve schedule information
  SET v_permitted_exec_delay_seconds =
    (
      SELECT
        s.permitted_exec_delay_seconds
      FROM
        ctl.dbjobz AS j
          INNER JOIN
        ctl.jobsch AS s
          ON
            j.schedule_id = s.schedule_id
      WHERE
        j.db_id = p_db_id AND 
        j.job_id = p_job_id AND
        NOT j.is_deleted AND
        NOT s.is_deleted
      WITH CS
    );

  -- Attempt to reserve the execution (returning the new reserve identifier if successful).
  SET p_reserved_id = 
    (
      SELECT 
        reserved_id
      FROM
        FINAL TABLE
        (
          UPDATE ctl.dbjobz
          SET
            reserved_until_timestamp = p_exec_timestamp + v_permitted_exec_delay_seconds SECONDS,
            reserved_id = NEXT VALUE FOR ctl.reserved_id
          WHERE
            db_id = p_db_id AND 
            job_id = p_job_id AND
            NOT is_deleted AND
            (reserved_until_timestamp IS NULL OR v_utc > reserved_until_timestamp)
        )
    );
END@

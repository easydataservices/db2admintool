--------------------------------------------------------------------------------------------------------------------------------
-- Script:      add_job.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Stored procedure CTL.SCHEDULE.ADD_JOB
--              Creates a new job.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD PROCEDURE add_job
(
  p_db_id INTEGER,
  p_exec_id INTEGER, 
  OUT p_job_id INTEGER
)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_is_deleted BOOLEAN;
  
  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;

  -- If a matching job already exists then retrieve it (undeleting if required) and early out.
  SET (p_job_id, v_is_deleted) =
    (
      SELECT
        job_id, is_deleted
      FROM
        ctl.dbjobz
      WHERE
        db_id = p_db_id AND 
        exec_id = p_exec_id
      WITH RS
    );
  IF p_job_id IS NOT NULL THEN
    IF v_is_deleted THEN
      UPDATE ctl.dbjobz SET is_deleted = FALSE, updated_timestamp = v_utc WHERE job_id = p_job_id;
    END IF;
    RETURN;
  END IF;

  -- Add the new job.
  SET p_job_id = (SELECT COALESCE(MAX(job_id) + 1, 1) AS job_id FROM ctl.dbjobz WHERE db_id = p_db_id WITH RR);
  INSERT INTO ctl.dbjobz(db_id, job_id, exec_id, created_timestamp, updated_timestamp)
  VALUES
    (p_db_id, p_job_id, p_exec_id, v_utc, v_utc);
END@

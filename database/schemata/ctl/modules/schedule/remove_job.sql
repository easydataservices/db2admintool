--------------------------------------------------------------------------------------------------------------------------------
-- Script:      remove_job.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Stored procedure CTL.SCHEDULE.REMOVE_JOB
--              Deletes a job.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD PROCEDURE remove_job(p_db_id INTEGER, p_job_id INTEGER)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_is_deleted BOOLEAN;
  
  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;

  -- If the job is not deleted then attempt to delete it.
  SET v_is_deleted =
    (
      SELECT
        is_deleted
      FROM
        ctl.dbjobz
      WHERE
        db_id = p_db_id AND
        job_id = p_job_id
      WITH RS
    );
  IF NOT v_is_deleted THEN
    UPDATE ctl.dbjobz SET is_deleted = TRUE, updated_timestamp = v_utc WHERE db_id = p_db_id AND job_id = p_job_id;
  END IF;
END@

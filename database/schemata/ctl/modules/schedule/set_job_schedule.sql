--------------------------------------------------------------------------------------------------------------------------------
-- Script:      set_job_schedule.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Stored procedure CTL.SCHEDULE.SET_JOB_SCHEDULE
--              Sets (or unsets) the schedule for a job.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD PROCEDURE set_job_schedule(p_db_id INTEGER, p_job_id INTEGER, p_schedule_id INTEGER)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  
  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;

  -- Update the schedule of the specified job.
  UPDATE ctl.dbjobz SET schedule_id = p_schedule_id, updated_timestamp = v_utc WHERE db_id = p_db_id AND job_id = p_job_id;
END@

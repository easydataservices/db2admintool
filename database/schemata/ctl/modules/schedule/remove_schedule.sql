--------------------------------------------------------------------------------------------------------------------------------
-- Script:      remove_schedule.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.14
--
-- Description: Stored procedure CTI.SCHEDULE.REMOVE_SCHEDULE
--              Deletes an unused schedule.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD PROCEDURE remove_schedule(p_schedule_id INTEGER)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_is_deleted BOOLEAN;
  
  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;

  -- If the schedule is not deleted then attempt to delete it.
  SET v_is_deleted =
    (
      SELECT
        is_deleted
      FROM
        ctl.jobsch
      WHERE
        schedule_id = p_schedule_id
      WITH RS
    );
  IF NOT v_is_deleted THEN
    UPDATE ctl.jobsch SET is_deleted = TRUE, updated_timestamp = v_utc WHERE schedule_id = p_schedule_id;
  END IF;
END@

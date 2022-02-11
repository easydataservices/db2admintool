--------------------------------------------------------------------------------------------------------------------------------
-- Script:      is_load_window.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Function CTL.SCHEDULE.IS_LOAD_WINDOW
--              Returns TRUE if the specified execution time is within current load window of the specified schedule; otherwise
--              FALSE.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD FUNCTION is_load_window(p_schedule_id INTEGER, p_exec_timestamp TIMESTAMP(0)) RETURNS BOOLEAN
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_permitted_load_delay_seconds SMALLINT;

  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;
      
  -- Retrieve schedule information.
  SET v_permitted_load_delay_seconds =
    (
      SELECT permitted_load_delay_seconds FROM ctl.jobsch WHERE schedule_id = p_schedule_id AND NOT is_deleted WITH CS
    );
  
  -- If the current time is within the load window then return TRUE; otherwise
  IF v_utc BETWEEN p_exec_timestamp AND p_exec_timestamp + v_permitted_load_delay_seconds SECONDS THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
END@

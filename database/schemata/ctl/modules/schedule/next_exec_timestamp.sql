--------------------------------------------------------------------------------------------------------------------------------
-- Script:      next_exec_timestamp.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Function CTI.SCHEDULE.NEXT_EXEC_TIMESTAMP
--              Returns the next execution time of the specified schedule. The time returned accounts for permitted load delay.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD FUNCTION next_exec_timestamp(p_schedule_id INTEGER) RETURNS TIMESTAMP(0)
BEGIN
  DECLARE v_next_exec_timestamp TIMESTAMP(0);
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_schedule_type VARCHAR(20);
  DECLARE v_start_timestamp TIMESTAMP(0);
  DECLARE v_permitted_load_delay_seconds SMALLINT;
  DECLARE v_repeat_interval SMALLINT;
  DECLARE v_epoch_seconds_start BIGINT;
  DECLARE v_epoch_seconds_now BIGINT;
  DECLARE v_diff_seconds BIGINT;

  -- Retrieve schedule information.
  SET (v_schedule_type, v_start_timestamp, v_permitted_load_delay_seconds, v_repeat_interval) =
    (
      SELECT
        schedule_type, start_timestamp, permitted_load_delay_seconds, repeat_interval
      FROM
        ctl.jobsch
      WHERE
        schedule_id = p_schedule_id AND NOT is_deleted
      WITH CS
    );

  -- Get UTC for execution. The load delay is subtracted.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE - v_permitted_load_delay_seconds SECONDS;

  -- Calculate the next execution time.
  IF v_utc <= v_start_timestamp THEN
    SET v_next_exec_timestamp = v_start_timestamp;
  ELSEIF v_schedule_type = 'NO_REPEAT' THEN
    SET v_next_exec_timestamp = v_start_timestamp;
  ELSE
    SET v_epoch_seconds_start = DATE_PART('EPOCH', v_start_timestamp);
    SET v_epoch_seconds_now = DATE_PART('EPOCH', v_utc);
    SET v_diff_seconds = v_epoch_seconds_now - v_epoch_seconds_start;
    IF v_schedule_type = 'EVERY_N_MINUTES' THEN
      SET v_next_exec_timestamp = v_utc + (v_repeat_interval * 60) SECONDS - MOD(v_diff_seconds, v_repeat_interval * 60) SECONDS;
    ELSEIF v_schedule_type = 'EVERY_N_HOURS' THEN
      SET v_next_exec_timestamp = v_utc + (v_repeat_interval * 3600) SECONDS - MOD(v_diff_seconds, v_repeat_interval * 3600) SECONDS;
    ELSEIF v_schedule_type = 'EVERY_N_DAYS' THEN
      SET v_next_exec_timestamp = v_utc + (v_repeat_interval * 86400) SECONDS - MOD(v_diff_seconds, v_repeat_interval * 86400) SECONDS;
    ELSEIF v_schedule_type = 'EVERY_N_WEEKS' THEN
      SET v_next_exec_timestamp = v_utc + (v_repeat_interval * 604800) SECONDS - MOD(v_diff_seconds, v_repeat_interval * 604800) SECONDS;
    END IF;
  END IF;
      
  -- Return result.
  RETURN v_next_exec_timestamp;
END@

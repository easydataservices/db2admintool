--------------------------------------------------------------------------------------------------------------------------------
-- Script:      get_poll_delay.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.11.06
--
-- Description: Table function CTL.SCHEDULE.GET_POLL_DELAY
--              Returns polling interval information used by scheduler.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD FUNCTION get_poll_delay(p_db_id INTEGER, p_exec_type VARCHAR(20))
  RETURNS TABLE
  (
    next_poll_timestamp TIMESTAMP(0),
    next_poll_delay_milliseconds INTEGER
  )
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_utc_with_milliseconds TIMESTAMP(3);
  DECLARE v_next_exec_timestamp TIMESTAMP(0);
  DECLARE v_poll_delay_milliseconds INTEGER DEFAULT 5000;

  -- Get UTC for execution.
  SET v_utc_with_milliseconds = CURRENT_TIMESTAMP(3) - CURRENT_TIMEZONE;
  SET v_utc = v_utc_with_milliseconds;

  -- Look up the next execution time.
  SET v_next_exec_timestamp =
    (
      SELECT
        MIN(j.next_exec_timestamp)
      FROM
        (
          SELECT
            exec_id,
            next_exec_timestamp(schedule_id) AS next_exec_timestamp
          FROM
            ctl.dbjobz
          WHERE
            db_id = p_db_id AND
            NOT is_deleted
        ) AS j
          INNER JOIN
        ctl.execla AS c
          ON
            j.exec_id = c.exec_id AND
            c.exec_type = p_exec_type
      WITH RS
    );

  -- Calculate and return the poll delay information.
  IF v_next_exec_timestamp IS NULL OR v_next_exec_timestamp >= v_utc + 30 SECONDS THEN
    SET v_poll_delay_milliseconds = 30000 - (MICROSECOND(v_utc_with_milliseconds) / 1000);
    PIPE (v_utc + 30 SECONDS, v_poll_delay_milliseconds);
  ELSE
    SET v_poll_delay_milliseconds =
      MAX(0, (SECONDS_BETWEEN(v_next_exec_timestamp, v_utc) * 1000) - (MICROSECOND(v_utc_with_milliseconds) / 1000));    
    PIPE (v_next_exec_timestamp, v_poll_delay_milliseconds);
  END IF;
  RETURN;
END@

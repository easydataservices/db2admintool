--------------------------------------------------------------------------------------------------------------------------------
-- Script:      add_schedule.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Stored procedure CTI.SCHEDULE.ADD_SCHEDULE
--              Creates a new schedule.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD PROCEDURE add_schedule
(
  p_schedule_type VARCHAR(20), 
  p_start_timestamp TIMESTAMP(0),
  p_repeat_interval SMALLINT,
  OUT p_schedule_id INTEGER
)
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_is_deleted BOOLEAN;
  
  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;

  -- If a matching schedule already exists then retrieve it (undeleting if required) and early out.
  SET (p_schedule_id, v_is_deleted) =
    (
      SELECT
        schedule_id, is_deleted
      FROM
        ctl.jobsch
      WHERE
        schedule_type = p_schedule_type AND 
        start_timestamp = p_start_timestamp AND
        (
          repeat_interval = p_repeat_interval OR
          repeat_interval IS NULL AND p_repeat_interval IS NULL
        )
      WITH RS
    );
  IF p_schedule_id IS NOT NULL THEN
    IF v_is_deleted THEN
      UPDATE ctl.jobsch SET is_deleted = FALSE, updated_timestamp = v_utc WHERE schedule_id = p_schedule_id;
    END IF;
    RETURN;
  END IF;

  -- Add the new schedule.
  SET p_schedule_id = (SELECT COALESCE(MAX(schedule_id) + 1, 1) AS schedule_id FROM ctl.jobsch WITH RR);
  INSERT INTO ctl.jobsch(schedule_id, schedule_type, start_timestamp, repeat_interval, created_timestamp, updated_timestamp)
  VALUES
    (p_schedule_id, p_schedule_type, p_start_timestamp, p_repeat_interval, v_utc, v_utc);
END@

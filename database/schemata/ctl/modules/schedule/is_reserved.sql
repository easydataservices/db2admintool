--------------------------------------------------------------------------------------------------------------------------------
-- Script:      is_reserved.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.31
--
-- Description: Function CTL.SCHEDULE.IS_RESERVED
--              Returns TRUE if the specified job is reserved by the specified reserved identifier (or by any if the specified 
--              reserved identifier is NULL); otherwise FALSE.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD FUNCTION is_reserved(p_db_id INTEGER, p_job_id INTEGER, p_reserved_id INTEGER) RETURNS BOOLEAN
BEGIN
  DECLARE v_utc TIMESTAMP(0);
  DECLARE v_result BOOLEAN;

  -- Get UTC for execution.
  SET v_utc = CURRENT_TIMESTAMP - CURRENT_TIMEZONE;

  -- Calculate result.
  SET v_result =
    (
      SELECT
        TRUE
      FROM
        ctl.dbjobz
      WHERE
        db_id = p_db_id AND 
        job_id = p_job_id AND
        NOT is_deleted AND
        v_utc <= reserved_until_timestamp AND
        (p_reserved_id IS NULL OR reserved_id = p_reserved_id)
      WITH CS
    );
  SET v_result = COALESCE(v_result, FALSE);

  -- Return result.
  RETURN v_result;
END@

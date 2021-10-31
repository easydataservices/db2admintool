--------------------------------------------------------------------------------------------------------------------------------
-- Script:      get_next_job.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Stored procedure CTI.SCHEDULE.GET_NEXT_JOB
--              Gets next job to be executed by the invoking process.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.schedule
ADD PROCEDURE get_next_job
(
  p_db_id INTEGER,
  p_exec_type VARCHAR(20),
  OUT p_job_id INTEGER,
  OUT p_next_exec_timestamp TIMESTAMP(0),
  OUT p_reserved_id INTEGER,
  OUT p_exec_class VARCHAR(120)
)
BEGIN
  -- Iterate through unreserved jobs with a next execution within the current load window...
  FOR r AS c1 CURSOR WITH HOLD FOR
    SELECT
      j.job_id,
      j.next_exec_timestamp,
      c.exec_class  
    FROM
      (
        SELECT
          job_id,
          exec_id,
          schedule_id,
          next_exec_timestamp(schedule_id) AS next_exec_timestamp
        FROM
          ctl.dbjobz
        WHERE
          db_id = p_db_id AND
          NOT is_deleted AND
          NOT is_reserved(db_id, job_id, NULL)
      ) AS j
        INNER JOIN
      ctl.execla AS c
        ON
          j.exec_id = c.exec_id AND
          c.exec_type = p_exec_type
    WHERE
      is_load_window(j.schedule_id, j.next_exec_timestamp)
    ORDER BY
      j.next_exec_timestamp
    WITH RS
  DO
    -- Attempt to reserve job execution.
    CALL reserve_exec(p_db_id, r.job_id, r.next_exec_timestamp, p_reserved_id);

    -- If reservation successful then return job details.
    IF p_reserved_id IS NOT NULL THEN
      SET p_job_id = r.job_id;
      SET p_next_exec_timestamp = r.next_exec_timestamp;
      SET p_exec_class = r.exec_class;
      RETURN;
    END IF;
    
    -- Otherwise continue iteration until successful or there are no further candidate jobs.
  END FOR;
END@

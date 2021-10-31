--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_schedule.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Module CTL.SCHEDULE
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE ctl.schedule;

ALTER MODULE ctl.schedule
PUBLISH PROCEDURE add_schedule
(
  p_schedule_type VARCHAR(20), 
  p_start_timestamp TIMESTAMP(0),
  p_repeat_interval SMALLINT,
  OUT p_schedule_id INTEGER
);

ALTER MODULE ctl.schedule
PUBLISH PROCEDURE remove_schedule(p_schedule_id INTEGER);

ALTER MODULE ctl.schedule
PUBLISH PROCEDURE add_job(p_db_id INTEGER, p_exec_id INTEGER,  OUT p_job_id INTEGER);

ALTER MODULE ctl.schedule
PUBLISH PROCEDURE remove_job(p_db_id INTEGER, p_job_id INTEGER);

ALTER MODULE ctl.schedule
PUBLISH PROCEDURE set_job_schedule(p_db_id INTEGER, p_job_id INTEGER, p_schedule_id INTEGER);

ALTER MODULE ctl.schedule
PUBLISH PROCEDURE get_next_job
(
  p_db_id INTEGER,
  p_exec_type VARCHAR(20),
  OUT p_job_id INTEGER,
  OUT p_next_exec_timestamp TIMESTAMP(0),
  OUT p_reserved_id INTEGER,
  OUT p_exec_class VARCHAR(120)
);

ALTER MODULE ctl.schedule
PUBLISH PROCEDURE reserve_exec(p_db_id INTEGER, p_job_id INTEGER, p_exec_timestamp TIMESTAMP(0), OUT p_reserved_id INTEGER);

ALTER MODULE ctl.schedule
PUBLISH FUNCTION next_exec_timestamp(p_schedule_id INTEGER) RETURNS TIMESTAMP(0);

ALTER MODULE ctl.schedule
PUBLISH FUNCTION is_exec_window(p_schedule_id INTEGER, p_exec_timestamp TIMESTAMP(0)) RETURNS BOOLEAN;

ALTER MODULE ctl.schedule
PUBLISH FUNCTION is_load_window(p_schedule_id INTEGER, p_exec_timestamp TIMESTAMP(0)) RETURNS BOOLEAN;

ALTER MODULE ctl.schedule
PUBLISH FUNCTION is_reserved(p_db_id INTEGER, p_job_id INTEGER, p_reserved_id INTEGER) RETURNS BOOLEAN;

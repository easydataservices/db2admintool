--------------------------------------------------------------------------------------------------------------------------------
-- Script:      jobsch.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Table CTL.JOBSCH (Job Schedule)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ctl.jobsch
(
  schedule_id                    INTEGER NOT NULL,
  schedule_type                  VARCHAR(20) NOT NULL,
  start_timestamp                TIMESTAMP(0) NOT NULL,
  permitted_load_delay_seconds   SMALLINT NOT NULL DEFAULT 10,
  permitted_exec_delay_seconds   SMALLINT NOT NULL DEFAULT 30,
  repeat_interval                SMALLINT,
  created_timestamp              TIMESTAMP(0) NOT NULL,
  updated_timestamp              TIMESTAMP(0) NOT NULL,
  is_deleted                     BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_pk PRIMARY KEY (schedule_id);

-- Consider adding support for EVERY_N_MONTHS and NTH_DOW_IN_MONTH.
ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc01 CHECK
(
  schedule_type IN
  (
    'NO_REPEAT',
    'EVERY_N_MINUTES',
    'EVERY_N_HOURS',
    'EVERY_N_DAYS',
    'EVERY_N_WEEKS'
  )
);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc02 CHECK (permitted_exec_delay_seconds >= 5);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc03 CHECK (permitted_load_delay_seconds BETWEEN 0 AND permitted_exec_delay_seconds - 5);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc04 CHECK (schedule_type = 'NO_REPEAT' OR repeat_interval > 0);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc05 CHECK (schedule_type != 'EVERY_N_MINUTES' OR repeat_interval < 120);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc06 CHECK (schedule_type != 'EVERY_N_HOURS' OR repeat_interval < 48);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc07 CHECK (schedule_type != 'EVERY_N_DAYS' OR repeat_interval < 14);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc08 CHECK (schedule_type != 'EVERY_N_WEEKS' OR repeat_interval < 9);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc09 CHECK (schedule_type != 'EVERY_N_MONTHS' OR repeat_interval < 13);

ALTER TABLE ctl.jobsch
ADD CONSTRAINT jobsch_cc10 CHECK (schedule_type != 'NTH_DOW_IN_MONTH' OR repeat_interval BETWEEN 0 AND 5);

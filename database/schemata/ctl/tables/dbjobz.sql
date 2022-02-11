--------------------------------------------------------------------------------------------------------------------------------
-- Script:      dbjobz.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: Table CTL.DBJOBZ (Database Job)
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ctl.dbjobz
(
  db_id                          INTEGER NOT NULL,
  job_id                         INTEGER NOT NULL,
  exec_id                        INTEGER NOT NULL,
  schedule_id                    INTEGER,
  last_exec_timestamp            TIMESTAMP(0),
  reserved_until_timestamp       TIMESTAMP(0),
  reserved_id                    INTEGER,
  created_timestamp              TIMESTAMP(0) NOT NULL,
  updated_timestamp              TIMESTAMP(0) NOT NULL,
  is_deleted                     BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE ctl.dbjobz
ADD CONSTRAINT dbjobz_pk PRIMARY KEY (db_id, job_id);

ALTER TABLE ctl.dbjobz
ADD CONSTRAINT dbjobz_jobsch_fk1 FOREIGN KEY (schedule_id) REFERENCES ctl.jobsch;

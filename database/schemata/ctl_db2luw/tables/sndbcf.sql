--------------------------------------------------------------------------------------------------------------------------------
-- Script:      sndbcf.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.23
--
-- Description: Table CTL_DB2LUW.SNDBCF
--------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ctl_db2luw.sndbcf
(
  db_id                          INTEGER NOT NULL,
  snapshot_timestamp             TIMESTAMP(0) NOT NULL,
  transform_timestamp            TIMESTAMP(0)
);

ALTER TABLE ctl_db2luw.sndbcf
ADD CONSTRAINT sndbcf_pk PRIMARY KEY (db_id, snapshot_timestamp);

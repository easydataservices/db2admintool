--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_transform.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Module CTL_DB2LUW.TRANSFORM (transform Db2 LUW snapshots into repository)
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE ctl_db2luw.transform;

ALTER MODULE ctl_db2luw.transform
PUBLISH PROCEDURE transform_sndbcf();

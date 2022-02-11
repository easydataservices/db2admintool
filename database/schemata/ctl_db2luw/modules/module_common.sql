--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_common.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Module CTL_DB2LUW.COMMON
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE ctl_db2luw.common;

ALTER MODULE ctl_db2luw.common
PUBLISH FUNCTION get_db_id(p_db_name VARCHAR(18), p_db_seed VARCHAR(10)) RETURNS INTEGER;

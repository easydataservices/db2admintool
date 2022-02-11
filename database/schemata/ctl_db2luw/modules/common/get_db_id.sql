--------------------------------------------------------------------------------------------------------------------------------
-- Script:      get_db_id.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Function CTL_DB2LUW.COMMON.REGISTER_DB
--              Returns identifier of database matching specified inputs; or NULL if there is no match.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl_db2luw.common
ADD FUNCTION get_db_id(p_db_name VARCHAR(18), p_db_seed VARCHAR(10)) RETURNS INTEGER
BEGIN
  RETURN
    (SELECT db_id FROM ctl.databa WHERE db_name = p_db_name AND db_seed = p_db_seed AND db_type = 'DB2LUW' WITH CS);
END@

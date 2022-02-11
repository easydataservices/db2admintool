--------------------------------------------------------------------------------------------------------------------------------
-- Script:      get_agent_id.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.12
--
-- Description: Function STG_DB2LUW.ADMIN.GET_AGENT_ID
--              Returns identifier of agent matching specified inputs; or NULL if there is no match.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE stg_db2luw.admin
ADD FUNCTION get_agent_id(p_auth_token VARCHAR(32)) RETURNS INTEGER
BEGIN
  RETURN
    (SELECT agent_id FROM ctl.dbrole WHERE auth_token = p_auth_token WITH CS);
END@

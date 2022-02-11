--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_security.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.14
--
-- Description: Module CTL.SECURITY
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE MODULE ctl.security;

ALTER MODULE ctl.security
PUBLISH PROCEDURE add_agent
(
  p_db_id INTEGER, 
  p_user_name VARCHAR(128), 
  p_host_name VARCHAR(255),
  OUT p_agent_id INTEGER
);

ALTER MODULE ctl.security
PUBLISH PROCEDURE set_role
(
  p_db_id INTEGER, 
  p_role VARCHAR(20), 
  p_agent_id INTEGER, 
  p_is_granted BOOLEAN,
  OUT p_auth_token VARCHAR(32)
);

ALTER MODULE ctl.security
PUBLISH FUNCTION generate_auth_token() RETURNS VARCHAR(32);

ALTER MODULE ctl.security
PUBLISH FUNCTION is_role_granted(p_db_id INTEGER, p_role VARCHAR(20), p_auth_token VARCHAR(32)) RETURNS BOOLEAN;

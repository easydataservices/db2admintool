--------------------------------------------------------------------------------------------------------------------------------
-- Script:      module_security.sql
-- Licence:     GNU General Public License v3.0
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
  p_client_hostname VARCHAR(255),
  p_client_ip_address VARCHAR(128),
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

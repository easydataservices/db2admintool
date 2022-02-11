--------------------------------------------------------------------------------------------------------------------------------
-- Script:      is_role_granted.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.14
--
-- Description: Function CTL.SECURITY.IS_ROLE_GRANTED
--              Returns TRUE if the specified role is granted to the user; otherwise FALSE.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.security
ADD FUNCTION is_role_granted(p_db_id INTEGER, p_role VARCHAR(20), p_auth_token VARCHAR(32)) RETURNS BOOLEAN
BEGIN
  DECLARE v_result BOOLEAN;
  
  SET v_result =
    (
      SELECT TRUE FROM ctl.dbrole WHERE db_id = p_db_id AND role = p_role AND auth_token = p_auth_token AND is_granted WITH CS
    );
  SET v_result = COALESCE(v_result, FALSE);
  RETURN v_result;
END@

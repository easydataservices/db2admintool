--------------------------------------------------------------------------------------------------------------------------------
-- Script:      generate_auth_token.sql
-- Licence:     GNU General Public License v3.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.14
--
-- Description: Stored procedure CTI.SECURITY.GENERATE_AUTH_TOKEN
--              Generates an authorisation token. The token is unique in almost any circumstance.
--------------------------------------------------------------------------------------------------------------------------------

ALTER MODULE ctl.security
ADD FUNCTION generate_auth_token()
  RETURNS VARCHAR(32)
BEGIN
  DECLARE v_alpha VARCHAR(6) DEFAULT '';
  
  -- Generate some random letters
  WHILE LENGTH(v_alpha) < 6 DO
    SET v_alpha = v_alpha || CHR(65 + MOD(SMALLINT(RANDOM() * 32767), 26));
  END WHILE;
    
  -- Append GENERATE_UNIQUE() text and return result.
  RETURN v_alpha || RAWTOHEX(GENERATE_UNIQUE());
END@

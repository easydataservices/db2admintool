--------------------------------------------------------------------------------------------------------------------------------
-- Script:      execla.sql
-- Licence:     Apache License 2.0
-- Author:      jeremy.rickard@easydataservices.com
-- Version:     2021.10.30
--
-- Description: View CTL.EXECLA (Execution Class)
--------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE VIEW ctl.execla
(
  exec_id, exec_type, exec_class, description
) AS
VALUES
  (1, 'MONITOR', 'com.easydataservices.db2admintool.database.agent.db2luw.dao.monitor.DatabaseConfigSnapshotDao', 'Monitor database configuration');

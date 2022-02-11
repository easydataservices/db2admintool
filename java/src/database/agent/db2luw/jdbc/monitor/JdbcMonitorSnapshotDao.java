package com.easydataservices.db2admintool.database.agent.db2luw.jdbc.monitor;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.database.agent.db2luw.Db2DatabaseIdentity;
import com.easydataservices.db2admintool.database.agent.db2luw.MonitorSnapshotDao;

//------------------------------------------------------------------------------
// File:         JdbcMonitorSnapshotDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Parent class for monitor snapshots.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.17
 */ 
//------------------------------------------------------------------------------
@Repository
public abstract class JdbcMonitorSnapshotDao extends MonitorSnapshotDao
{
  private static final String className = JdbcMonitorSnapshotDao.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private JdbcTemplate dbJdbcTemplate;

  public JdbcTemplate getDbJdbcTemplate() {
    final String method = "getDbJdbcTemplate";

    logger.entering(className, method);
    if (dbJdbcTemplate == null) {
      dbJdbcTemplate = (JdbcTemplate) getContext().getBean("dbJdbcTemplate");
    }
    logger.exiting(className, method, dbJdbcTemplate);
    return dbJdbcTemplate;
  }
}
package com.easydataservices.db2admintool.agent.db2luw.dao.monitor;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

//------------------------------------------------------------------------------
// File:         SimpleMonitorSnapshotDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Parent class for monitor snapshots.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.17
 */ 
//------------------------------------------------------------------------------
@Repository
public abstract class SimpleMonitorSnapshotDao
{
  private static final String className = SimpleMonitorSnapshotDao.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  protected JdbcTemplate dbJdbcTemplate;
  protected JdbcTemplate repoJdbcTemplate;
  protected SimpleJdbcCall repoSimpleJdbcCall;
  protected String dbName = "EZSY";
  protected String dbSeed = "3065966854";
  protected String authToken = "FYQZNX20211015210801110303000000";
  
  @Autowired
  public void setDbJdbcTemplate(@Qualifier("dbJdbcTemplate") JdbcTemplate dbJdbcTemplate) {
    final String method = "setJdbcTemplate";
    
    logger.entering(this.className, method, new Object[] {dbJdbcTemplate});
    this.dbJdbcTemplate = dbJdbcTemplate;
    logger.exiting(this.className, method);   
  }

  @Autowired
  public void setRepoJdbcTemplate(@Qualifier("repoJdbcTemplate") JdbcTemplate repoJdbcTemplate) {
    final String method = "setJdbcTemplate";
    
    logger.entering(this.className, method, new Object[] {repoJdbcTemplate});
    this.repoJdbcTemplate = repoJdbcTemplate;
    logger.exiting(this.className, method);   
  }

  /**
   * Abstract method to capture a database snapshot and persist it in the repository.
   */
  public abstract void capture();
}
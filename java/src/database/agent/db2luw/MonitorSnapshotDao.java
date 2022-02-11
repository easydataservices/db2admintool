package com.easydataservices.db2admintool.database.agent.db2luw;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.database.agent.db2luw.Db2DatabaseIdentity;

//------------------------------------------------------------------------------
// File:         MonitorSnapshotDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Parent class for all monitor snapshot DAOs.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.12.04
 */ 
//------------------------------------------------------------------------------
@Repository
public abstract class MonitorSnapshotDao
{
  private static final String className = MonitorSnapshotDao.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private ApplicationContext context;
  private Db2DatabaseIdentity db2DatabaseIdentity;
  private JdbcTemplate repoJdbcTemplate;
  private String authToken;
  private Properties configProperties;
  
  public ApplicationContext getContext() {
    final String method = "getContext";
    
    logger.entering(className, method);
    logger.exiting(className, method, context);
    return context;
  }

  public Db2DatabaseIdentity getDb2DatabaseIdentity() {
    final String method = "getDb2DatabaseIdentity";
    
    logger.entering(className, method);
    logger.exiting(className, method, db2DatabaseIdentity);
    return db2DatabaseIdentity;
  }

  public JdbcTemplate getRepoJdbcTemplate() {
    final String method = "getRepoJdbcTemplate";

    logger.entering(className, method);
    if (repoJdbcTemplate == null) {
      repoJdbcTemplate = (JdbcTemplate) context.getBean("repoJdbcTemplate");
    }
    logger.exiting(className, method, repoJdbcTemplate);
    return repoJdbcTemplate;
  }

  public String getAuthToken() {
    final String method = "getAuthToken";

    logger.entering(className, method);
    if (authToken == null) {
      authToken = (String) context.getBean("authToken");
    }
    logger.exiting(className, method, "******");
    return authToken;
  }

  public Properties getConfigProperties() {
    final String method = "getConfigProperties";

    logger.entering(className, method);
    if (configProperties == null) {
      configProperties = (Properties) context.getBean("configProperties");
    }
    logger.exiting(className, method);
    return configProperties;
  }

  public void setContext(ApplicationContext context) {
    final String method = "setContext";
    
    logger.entering(className, method, new Object[] {context});
    this.context = context;
    logger.exiting(className, method);   
  }

  public void setDb2DatabaseIdentity(Db2DatabaseIdentity db2DatabaseIdentity) {
    final String method = "setDb2DatabaseIdentity";
    
    logger.entering(className, method, new Object[] {db2DatabaseIdentity});
    this.db2DatabaseIdentity = db2DatabaseIdentity;
    logger.exiting(className, method);   
  }

  /**
   * Abstract method to capture a database snapshot and persist it in the repository.
   */
  public abstract void capture();
}
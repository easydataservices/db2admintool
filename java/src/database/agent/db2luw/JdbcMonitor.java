package com.easydataservices.db2admintool.database.agent.db2luw;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SimpleCommandLinePropertySource;          
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.database.agent.JdbcAgent;
import com.easydataservices.db2admintool.database.agent.config.FileConfigClasses;
import com.easydataservices.db2admintool.database.agent.config.JdbcMonitorClasses;
import com.easydataservices.db2admintool.database.agent.db2luw.Db2DatabaseIdentity;
import com.easydataservices.db2admintool.database.agent.db2luw.Db2DatabaseIdentityDao;
import com.easydataservices.db2admintool.database.agent.db2luw.jdbc.monitor.JdbcMonitorSnapshotDao;
import com.easydataservices.db2admintool.repository.agent.db2luw.dao.ClientAdminDao;
import com.easydataservices.db2admintool.repository.scheduler.model.Job;
import com.easydataservices.db2admintool.repository.scheduler.model.PollDelay;
                                                                          
//------------------------------------------------------------------------------
// File:         JdbcMonitor.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Main class for a Db2 for LUW monitoring agent using a JDBC data source.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.06
 */
//------------------------------------------------------------------------------
@Repository
public class JdbcMonitor extends JdbcAgent {
  private static final String className = JdbcMonitor.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private ClientAdminDao clientAdminDao;
  private Db2DatabaseIdentityDao db2DatabaseIdentityDao;
  private Db2DatabaseIdentity db2DatabaseIdentity;

  /**
   * Return the process type.
   * @return Process type.
   */
  @Override
  protected String getProcessType() {
    return "JDBC_MONITOR";
  }

  /**
   * Validate supplied command line properties.
   */  
  @Override
  protected void validateProperties(SimpleCommandLinePropertySource propertySource) {
    final String method = "validateProperties";
    final String textUsage = "Usage: " + className 
      + " [ --repositoryFile=<path> ] [ --databaseFile=<path> ] [ --configFile=<path> ]";

    logger.entering(className, method, new Object[] {propertySource});
    if (!Arrays.asList(propertySource.getPropertyNames()).contains("configFile")) {
      throw new IllegalArgumentException("Argument --configFile missing");
    }
    if (propertySource.getPropertyNames().length > 3) {
      throw new IllegalArgumentException("Too many arguments\n" + textUsage);
    }
    logger.exiting(className, method);
  }

  /**
   * Register required classes in application context.
   */  
  @Override
  protected void registerClasses(AnnotationConfigApplicationContext context) {
    final String method = "registerClasses";

    logger.entering(className, method, new Object[] {context});
    super.registerClasses(context);
    context.register(Db2DatabaseIdentityDao.class);
    context.register(ClientAdminDao.class);
    context.register(FileConfigClasses.class);
    context.register(JdbcMonitorClasses.class);
    logger.exiting(className, method);
  }

  /**
   * Instantiate required classes from application context.
   */
  @Override
  protected void instantiateClasses(AnnotationConfigApplicationContext context) { 
    final String method = "instantiateClasses";

    logger.entering(className, method, new Object[] {context});
    db2DatabaseIdentityDao = context.getBean(Db2DatabaseIdentityDao.class);
    clientAdminDao = context.getBean(ClientAdminDao.class);
    logger.exiting(className, method);
  }

  /**
   * Execute.
   */
  @Override
  protected void exec(AnnotationConfigApplicationContext context) {
    final String method = "exec";
    
    logger.entering(className, method);
    Integer dbId = getDb2DatabaseIdentity().getDbId();
    while (true) {
      PollDelay pollDelay = getSchedulerDao().getPollDelay(dbId, getProcessType());
      if (pollDelay.getNextPollDelayMilliseconds() == null) {
        break;
      }
      if (pollDelay.getNextPollDelayMilliseconds().equals(0)) {
        Job job = getSchedulerDao().getNextJob(dbId, getProcessType());
        try {
          JdbcMonitorSnapshotDao snapshotDao = (JdbcMonitorSnapshotDao) Class.forName(job.getExecClass()).newInstance();
          snapshotDao.setContext(context);
          snapshotDao.setDb2DatabaseIdentity(getDb2DatabaseIdentity());
          snapshotDao.capture();
        }
        catch (Exception exception) {
          logger.logp(Level.WARNING, className, method, exception.getMessage());
        }
        try {
          Thread.sleep(10000);
        }
        catch (InterruptedException exception) {
          // Do nothing.
        }
      }
      else {
        try {
          Thread.sleep(pollDelay.getNextPollDelayMilliseconds());
        }
        catch (InterruptedException exception) {
          // Do nothing.
        }
      }
    }
    logger.exiting(className, method);
  }

  /**
   * Return Db2 database identity object.
   */
  protected Db2DatabaseIdentity getDb2DatabaseIdentity() {
    final String method = "getDb2DatabaseIdentity";

    logger.entering(className, method);
    db2DatabaseIdentity = db2DatabaseIdentityDao.getIdentity();
    Integer dbId = clientAdminDao.getDbId(db2DatabaseIdentity.getDbName(), db2DatabaseIdentity.getDbSeed());
    if (dbId == null) {
      throw new IllegalArgumentException("Database not registered");
    }
    db2DatabaseIdentity.setDbId(dbId);    
    logger.exiting(className, method, db2DatabaseIdentity);
    return db2DatabaseIdentity;
  }

  /**
   * Main process (entry point).
   */  
  public static void main(String[] args) {
    final String method = "main";
    
    logger.entering(className, method, args);
    try {
      JdbcAgent agent = new JdbcMonitor();
      agent.run(args);
    }
    catch (Exception exception) {
      logger.logp(Level.SEVERE, className, method, exception.getMessage() + "\nABORT");
    }
    logger.exiting(className, method);
  }
}

package com.easydataservices.db2admintool.database.agent;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.config.DatabaseConfig;
import com.easydataservices.db2admintool.database.agent.Agent;

//------------------------------------------------------------------------------
// File:         JdbcAgent.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Base abstract class for a database agent process using a JDBC data source.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.06
 */
//------------------------------------------------------------------------------
@Repository
public abstract class JdbcAgent extends Agent {
  private static final String className = JdbcAgent.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  /**
   * Register required classes in application context.
   */  
  protected void registerClasses(AnnotationConfigApplicationContext context) {
    final String method = "registerClasses";

    logger.entering(className, method, new Object[] {context});
    context.register(DatabaseConfig.class);
    logger.exiting(className, method);
  }
}

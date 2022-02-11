package com.easydataservices.db2admintool.database.agent;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.database.agent.Agent;

//------------------------------------------------------------------------------
// File:         ConsoleAgent.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Base abstract class for a database agent process using a console data source.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.21
 */
//------------------------------------------------------------------------------
@Repository
public abstract class ConsoleAgent extends Agent {
  private static final String className = ConsoleAgent.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  /**
   * Register required classes in application context.
   */  
  protected void registerClasses(AnnotationConfigApplicationContext context) {
    final String method = "registerClasses";

    logger.entering(className, method, new Object[] {context});
    logger.exiting(className, method);
  }
}

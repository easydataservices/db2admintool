package com.easydataservices.db2admintool.database.agent;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.config.BootstrapProperties;
import com.easydataservices.db2admintool.config.RepositoryConfig;
import com.easydataservices.db2admintool.repository.scheduler.dao.SchedulerDao;
import com.easydataservices.db2admintool.repository.scheduler.model.Job;
import com.easydataservices.db2admintool.repository.scheduler.model.PollDelay;

//------------------------------------------------------------------------------
// File:         Agent.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Base abstract class for a database agent process.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.06
 */
//------------------------------------------------------------------------------
@Repository
public abstract class Agent {
  private static final String className = Agent.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private AnnotationConfigApplicationContext context;
  private SchedulerDao schedulerDao;

  /**
   * Return the process type.
   * @return Process type.
   */  
  protected abstract String getProcessType();

  /**
   * Validate the supplied command line properties.
   */  
  protected abstract void validateProperties(SimpleCommandLinePropertySource propertySource);

  /**
   * Register required classes in application context.
   */  
  protected abstract void registerClasses(AnnotationConfigApplicationContext context);

  /**
   * Instantiate required classes from application context.
   */  
  protected abstract void instantiateClasses(AnnotationConfigApplicationContext context);

  /**
   * Execute (the required agent workload).
   */
  protected abstract void exec(AnnotationConfigApplicationContext context);

  /**
   * Return the application context.
   */
  protected AnnotationConfigApplicationContext getContext() {
    final String method = "getContext";

    logger.entering(className, method);
    logger.exiting(className, method, context);
    return context;
  }

  /**
   * Return the scheduler DAO.
   * @return Scheduler DAO.
   */  
  protected SchedulerDao getSchedulerDao() {
    final String method = "getSchedulerDao";

    logger.entering(className, method);
    logger.exiting(className, method, schedulerDao);
    return schedulerDao;
  }

  /**
   * Run (everything). This public method is called by the main method of subclasses that extend {@code Agent}.
   */
  public void run(String[] args) {
    final String method = "run";

    // Create application context.
    logger.entering(className, method, args);
    context = new AnnotationConfigApplicationContext();
    StandardEnvironment standardEnvironment = new StandardEnvironment();
    context.setEnvironment(standardEnvironment);

    // Add command line properties to context environment.
    SimpleCommandLinePropertySource propertySource = new SimpleCommandLinePropertySource(args);
    context.getEnvironment().getPropertySources().addFirst(propertySource);

    // Add extra properties (notably process type) to context environment.
    Map<String, Object> extraPropertyMap = new HashMap<String, Object>();
    extraPropertyMap.put("processType", getProcessType());
    context.getEnvironment().getPropertySources().addFirst(new MapPropertySource("extraPropertyMap", extraPropertyMap));
    
    // Validate properties.
    validateProperties(propertySource);         

    // Add required classes to application context.
    context.register(RepositoryConfig.class);
    context.register(SchedulerDao.class);
    registerClasses(context);
    context.refresh();

    // Instantiate classes.
    schedulerDao = context.getBean(SchedulerDao.class); 
    instantiateClasses(context);

    // Execute the supplied workload.
    exec(context);
    logger.exiting(className, method);
  }
}

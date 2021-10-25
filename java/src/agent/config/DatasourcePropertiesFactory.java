package com.easydataservices.db2admintool.agent.config;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.easydataservices.db2admintool.agent.config.DatasourceProperties;

//------------------------------------------------------------------------------
// File:         DatasourcePropertiesFactory.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Interface for all datasource properties.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.23
 */ 
//------------------------------------------------------------------------------
public class DatasourcePropertiesFactory {
  private static final String className = DatasourcePropertiesFactory.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  /**
   * Return new instance of specified class.
   */
  public DatasourceProperties getDatasourceProperties(String className) {
    final String method = "getDatasourceProperties";

    logger.entering(this.className, method, new Object[] {className});    
    DatasourceProperties newInstance = null;
    try {
      newInstance = (DatasourceProperties) Class.forName(className).newInstance();
    }
    catch (Exception exception) {
      logger.logp(Level.SEVERE, this.className, method, exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }
    logger.exiting(this.className, method, newInstance);    
    return newInstance;
  }
}

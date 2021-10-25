package com.easydataservices.db2admintool.agent.config;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.easydataservices.db2admintool.agent.config.DatabaseDatasourceProperties;

//------------------------------------------------------------------------------
// File:         DatabasePropertiesFromFile.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Properties for (source) database datasource.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.23
 */ 
//------------------------------------------------------------------------------
@Configuration
@PropertySource("file:agent.properties")
public class DatabasePropertiesFromFile implements DatabaseDatasourceProperties {
  @Autowired
  private Environment env;

  /**
   * Return class name of database driver.
   */
  public String getDriverClassName() {
    return env.getProperty("dbDriverClassName");
  }

  /**
   * Return database connection URL.
   */
  public String getConnectionUrl() {
    return env.getProperty("dbConnectionUrl");
  }

  /**
   * Return user name for connection.
   */
  public String getUserName() {
    return env.getProperty("dbUserName");
  }

  /**
   * Return password for connection.
   */
  public String getPassword() {
    return env.getProperty("dbPassword");
  }

  /**
   * Return default schema name.
   */
  public String getSchemaName() {
    return env.getProperty("dbSchema");
  }
}
                                   
package com.easydataservices.db2admintool.agent.config;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.easydataservices.db2admintool.agent.config.RepositoryDatasourceProperties;

//------------------------------------------------------------------------------
// File:         RepositoryPropertiesFromFile.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Properties for repository datasource.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.23
 */ 
//------------------------------------------------------------------------------
@Configuration
@PropertySource("file:repository.properties")
public class RepositoryPropertiesFromFile implements RepositoryDatasourceProperties {
  @Autowired
  private Environment env;

  /**
   * Return class name of database driver.
   */
  public String getDriverClassName() {
    return env.getProperty("repoDriverClassName");
  }

  /**
   * Return database connection URL.
   */
  public String getConnectionUrl() {
    return env.getProperty("repoConnectionUrl");
  }

  /**
   * Return user name for connection.
   */
  public String getUserName() {
    return env.getProperty("repoUserName");
  }

  /**
   * Return password for connection.
   */
  public String getPassword() {
    return env.getProperty("repoPassword");
  }

  /**
   * Return default schema name.
   */
  public String getSchemaName() {
    return env.getProperty("repoSchema");
  }
}
                                   
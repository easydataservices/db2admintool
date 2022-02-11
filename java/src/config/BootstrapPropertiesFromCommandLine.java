package com.easydataservices.db2admintool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import com.easydataservices.db2admintool.config.BootstrapProperties;

//------------------------------------------------------------------------------
// File:         BootstrapPropertiesFromCommandLine.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Default implementation for bootstrap properties.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.20
 */ 
//------------------------------------------------------------------------------
public class BootstrapPropertiesFromCommandLine implements BootstrapProperties {
  @Autowired
  private Environment env;

  /**
   * Return path to default properties file for repository datasource.
   */
  public String getRepositoryPropertiesPath() {
    String path = env.getProperty("repositoryFile");
    if (path == null || path.trim().isEmpty()) {
      path = "repository.properties";
    }
    return path;
  }

  /**
   * Return path to default properties file for database datasource.
   */
  public String getDatabasePropertiesPath() {
    String path = env.getProperty("databaseFile");
    if (path == null || path.trim().isEmpty()) {
      path = "database.properties";
    }
    return path;
  }

  /**
   * Return path to default configuration properties file.
   */
  public String getConfigPropertiesPath() {
    String path = env.getProperty("configFile");
    if (path == null || path.trim().isEmpty()) {
      path = "config.properties";
    }
    return path;
  }
}

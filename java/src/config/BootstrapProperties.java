package com.easydataservices.db2admintool.config;

//------------------------------------------------------------------------------
// File:         BootstrapProperties.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Interface for bootstrap properties.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.20
 */ 
//------------------------------------------------------------------------------
public interface BootstrapProperties {
  /**
   * Return location of properties for repository datasource.
   */
  public String getRepositoryPropertiesPath();

  /**
   * Return location of properties for database datasource.
   */
  public String getDatabasePropertiesPath();

  /**
   * Return location of configuration properties.
   */
  public String getConfigPropertiesPath();
}

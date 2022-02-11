package com.easydataservices.db2admintool.config;

//------------------------------------------------------------------------------
// File:         DatasourceProperties.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Interface for all datasource properties.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.23
 */ 
//------------------------------------------------------------------------------
public interface DatasourceProperties {
  /**
   * Return class name of database driver.
   */
  public String getDriverClassName();

  /**
   * Return database connection URL.
   */
  public String getConnectionUrl();

  /**
   * Return user name for connection.
   */
  public String getUserName();

  /**
   * Return password for connection.
   */
  public String getPassword();

  /**
   * Return default schema name.
   */
  public String getSchemaName();
}

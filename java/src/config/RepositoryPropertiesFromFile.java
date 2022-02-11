package com.easydataservices.db2admintool.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.easydataservices.db2admintool.config.RepositoryProperties;

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
public class RepositoryPropertiesFromFile implements RepositoryProperties {
  private static final String className = RepositoryPropertiesFromFile.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private Properties properties;
  
  @Autowired
  private BootstrapProperties bootstrapProperties;

  /**
   * Load properties from bootstrap configuration.
   */
  private void loadProperties() {
    final String method = "loadProperties";

    logger.entering(className, method);
    try (InputStream inputStream = new FileInputStream(bootstrapProperties.getRepositoryPropertiesPath())) {
      properties = new Properties();
      properties.load(inputStream);
    }
    catch (Exception exception) {
      logger.logp(Level.SEVERE, className, method, exception.toString());
      System.exit(4);
    }
    logger.exiting(className, method);
  }

  /**
   * Return class name of database driver.
   */
  public String getDriverClassName() {
    final String method = "getDriverClassName";

    logger.entering(className, method);
    if (properties == null) {
      loadProperties();
    }
    String dbDriverClassName = properties.getProperty("repoDriverClassName");
    logger.exiting(className, method, dbDriverClassName);
    return dbDriverClassName;
  }

  /**
   * Return database connection URL.
   */
  public String getConnectionUrl() {
    final String method = "getConnectionUrl";

    logger.entering(className, method);
    if (properties == null) {
      loadProperties();
    }
    String dbConnectionUrl = properties.getProperty("repoConnectionUrl");
    logger.exiting(className, method, dbConnectionUrl);
    return dbConnectionUrl;
  }

  /**
   * Return user name for connection.
   */
  public String getUserName() {
    final String method = "getUserName";

    logger.entering(className, method);
    if (properties == null) {
      loadProperties();
    }
    String dbUserName = properties.getProperty("repoUserName");
    logger.exiting(className, method, dbUserName);
    return dbUserName;
  }

  /**
   * Return password for connection.
   */
  public String getPassword() {
    final String method = "getPassword";

    logger.entering(className, method);
    if (properties == null) {
      loadProperties();
    }
    String dbPassword = properties.getProperty("repoPassword");
    logger.exiting(className, method, "*****");
    return dbPassword;
  }

  /**
   * Return default schema name.
   */
  public String getSchemaName() {
    final String method = "getSchemaName";

    logger.entering(className, method);
    if (properties == null) {
      loadProperties();
    }
    String dbSchema = properties.getProperty("repoSchema");
    logger.exiting(className, method, dbSchema);
    return dbSchema;
  }
}

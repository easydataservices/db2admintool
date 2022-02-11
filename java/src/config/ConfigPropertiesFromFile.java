package com.easydataservices.db2admintool.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.easydataservices.db2admintool.config.BootstrapProperties;
import com.easydataservices.db2admintool.config.ConfigProperties;

//------------------------------------------------------------------------------
// File:         ConfigPropertiesFromFile.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Default class for configuration properties.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.20
 */ 
//------------------------------------------------------------------------------
@Configuration
public class ConfigPropertiesFromFile implements ConfigProperties {
  private static final String className = ConfigPropertiesFromFile.class.getName();
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
    try (InputStream inputStream = new FileInputStream(bootstrapProperties.getConfigPropertiesPath())) {
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
   * Return authorisation token.
   */
  @Bean("authToken")
  public String getAuthToken() {
    final String method = "getAuthToken";

    logger.entering(className, method);
    if (properties == null) {
      loadProperties();
    }
    String authToken = properties.getProperty("authToken");
    logger.exiting(className, method, "*****");
    return authToken;
  }

  /**
   * Return all properties.
   */
  @Bean("configProperties")
  public Properties getConfigProperties() {
    final String method = "getConfigProperties";

    logger.entering(className, method);
    if (properties == null) {
      loadProperties();
    }
    logger.exiting(className, method);
    return properties;
  }
}
                                   
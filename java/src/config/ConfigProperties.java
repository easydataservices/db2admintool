package com.easydataservices.db2admintool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//------------------------------------------------------------------------------
// File:         ConfigProperties.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Interface for configuration properties.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.20
 */ 
//------------------------------------------------------------------------------
@Configuration
public interface ConfigProperties {
  /**                   
   * Return authorisation token.
   */
  @Bean("authToken")
  public String getAuthToken();
}

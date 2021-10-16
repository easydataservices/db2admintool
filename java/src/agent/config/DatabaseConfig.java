package com.easydataservices.db2admintool.agent.config;

import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//------------------------------------------------------------------------------
// File:         DatabaseConfig.java
// Licence:      GNU General Public License v3.0
// Description:  
/**
 * Configuration of agent local datasource and JDBC template.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.10
 */ 
//------------------------------------------------------------------------------
@Configuration
@PropertySource("file:agent.properties")
public class DatabaseConfig {
  private static final String className = DatabaseConfig.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  @Autowired
  private Environment env;

  /**
   * Configure repository data source.
   */
  @Bean("dataSource")
  public DataSource getDataSource() {
    final String method = "getDataSource";
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();

    logger.entering(this.className, method);
    dataSource.setDriverClassName(env.getProperty("dbDriverClassName"));
    dataSource.setUrl(env.getProperty("dbConnectionUrl"));
    if (env.getProperty("dbUserName") != null) {
      dataSource.setUsername(env.getProperty("dbUserName"));
      dataSource.setPassword(env.getProperty("dbPassword"));
    }
    if (env.getProperty("dbSchema") != null) {
      dataSource.setSchema(env.getProperty("dbSchema"));
    }
    logger.exiting(this.className, method);
    return dataSource;
  }

  /**
   * Configure {@code JdbcTemplate} bean.
   */
  @Bean("jdbcTemplate")
  public JdbcTemplate getJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
    final String method = "getJdbcTemplate";

    logger.entering(this.className, method);
    logger.exiting(this.className, method);
    return new JdbcTemplate(dataSource);
  }
}
                                   

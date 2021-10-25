package com.easydataservices.db2admintool.agent.config;

import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.easydataservices.db2admintool.agent.config.DatabaseDatasourceProperties;

//------------------------------------------------------------------------------
// File:         DatabaseConfig.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Configuration of (source) database datasource and JDBC template.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.23
 */ 
//------------------------------------------------------------------------------
@Configuration
public class DatabaseConfig {
  private static final String className = DatabaseConfig.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  @Autowired
  private DatabaseDatasourceProperties properties;

  /**
   * Configure repository data source.
   */
  @Bean("dbDataSource")
  public DataSource getDataSource() {
    final String method = "getDataSource";
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();

    logger.entering(this.className, method);
    dataSource.setDriverClassName(properties.getDriverClassName());
    dataSource.setUrl(properties.getConnectionUrl());
    if (properties.getUserName() != null) {
      dataSource.setUsername(properties.getUserName());
      dataSource.setPassword(properties.getPassword());
    }
    if (properties.getSchemaName() != null) {
      dataSource.setSchema(properties.getSchemaName());
    }
    logger.exiting(this.className, method, dataSource);
    return dataSource;
  }

  /**
   * Configure {@code JdbcTemplate} bean.
   */
  @Bean("dbJdbcTemplate")
  public JdbcTemplate getJdbcTemplate(@Qualifier("dbDataSource") DataSource dataSource) {
    final String method = "getJdbcTemplate";
    JdbcTemplate jdbcTemplate;

    logger.entering(this.className, method, new Object[] {dataSource});
    jdbcTemplate = new JdbcTemplate(dataSource);
    logger.exiting(this.className, method, jdbcTemplate);
    return jdbcTemplate;
  }
}

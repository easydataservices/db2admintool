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
// File:         RepositoryConfig.java
// Licence:      GNU General Public License v3.0
// Description:  
/**
 * Configuration of repository datasource and JDBC template.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.17
 */ 
//------------------------------------------------------------------------------
@Configuration
@PropertySource("file:repository.properties")
public class RepositoryConfig {
  private static final String className = RepositoryConfig.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  @Autowired
  private Environment env;

  /**
   * Configure repository data source.
   */
  @Bean("repoDataSource")
  public DataSource getDataSource() {
    final String method = "getDataSource";
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();

    logger.entering(this.className, method);
    dataSource.setDriverClassName(env.getProperty("repoDriverClassName"));
    dataSource.setUrl(env.getProperty("repoConnectionUrl"));
    if (env.getProperty("repoUserName") != null) {
      dataSource.setUsername(env.getProperty("repoUserName"));
      dataSource.setPassword(env.getProperty("repoPassword"));
    }
    if (env.getProperty("repoSchema") != null) {
      dataSource.setSchema(env.getProperty("repoSchema"));
    }
    logger.exiting(this.className, method);
    return dataSource;
  }

  /**
   * Configure {@code JdbcTemplate} bean.
   */
  @Bean("repoJdbcTemplate")
  public JdbcTemplate getJdbcTemplate(@Qualifier("repoDataSource") DataSource dataSource) {
    final String method = "getJdbcTemplate";

    logger.entering(this.className, method);
    logger.exiting(this.className, method);
    return new JdbcTemplate(dataSource);
  }
}

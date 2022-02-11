package com.easydataservices.db2admintool.database.agent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.easydataservices.db2admintool.database.agent.db2luw.jdbc.monitor.syntrn.Dao;

//------------------------------------------------------------------------------
// File:         JdbcMonitorClasses.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Configuration of supported JDBC based monitors.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.21
 */ 
//------------------------------------------------------------------------------
@Configuration
@Import(
  {
    Dao.class
  }
)
public class JdbcMonitorClasses {
}

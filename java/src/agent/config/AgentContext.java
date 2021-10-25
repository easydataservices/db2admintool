package com.easydataservices.db2admintool.agent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.easydataservices.db2admintool.agent.config.DatabaseConfig;
import com.easydataservices.db2admintool.agent.config.DatabasePropertiesFromFile;
import com.easydataservices.db2admintool.agent.config.RepositoryConfig;
import com.easydataservices.db2admintool.agent.config.RepositoryPropertiesFromFile;
import com.easydataservices.db2admintool.agent.db2luw.dao.monitor.DatabaseConfigSnapshotDao;

//------------------------------------------------------------------------------
// File:         AgentContext.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Agent application context configuration.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.10
 */ 
//------------------------------------------------------------------------------
@Configuration
@Import(
  {
    DatabasePropertiesFromFile.class,
    RepositoryPropertiesFromFile.class,      
    DatabaseConfig.class,
    RepositoryConfig.class,
    DatabaseConfigSnapshotDao.class
  }
)
public class AgentContext {
}

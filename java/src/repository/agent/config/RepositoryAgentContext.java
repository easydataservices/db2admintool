package com.easydataservices.db2admintool.repository.agent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.easydataservices.db2admintool.config.BootstrapPropertiesFromCommandLine;
import com.easydataservices.db2admintool.config.RepositoryPropertiesFromFile;
import com.easydataservices.db2admintool.config.RepositoryConfig;
import com.easydataservices.db2admintool.repository.agent.db2luw.dao.SnapshotTransformDao;


//------------------------------------------------------------------------------
// File:         RepositoryAgentContext.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Repository agent application context configuration.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.23
 */ 
//------------------------------------------------------------------------------
@Configuration
@Import(
  {
    BootstrapPropertiesFromCommandLine.class,
    RepositoryPropertiesFromFile.class,
    RepositoryConfig.class,
    SnapshotTransformDao.class
  }
)
public class RepositoryAgentContext {
}

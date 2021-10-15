package com.easydataservices.db2admintool.agent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.easydataservices.db2admintool.agent.config.DatabaseConfig;
import com.easydataservices.db2admintool.agent.db2luw.dao.local.DatabaseSnapshotDao;

//------------------------------------------------------------------------------
// File:         AgentContext.java
// Licence:      GNU General Public License v3.0
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
    DatabaseConfig.class,
    DatabaseSnapshotDao.class
  }
)
public class AgentContext {
}

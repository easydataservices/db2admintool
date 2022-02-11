package com.easydataservices.db2admintool.database.agent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.easydataservices.db2admintool.config.BootstrapPropertiesFromCommandLine;
import com.easydataservices.db2admintool.config.ConfigPropertiesFromFile;
import com.easydataservices.db2admintool.config.DatabasePropertiesFromFile;
import com.easydataservices.db2admintool.config.RepositoryPropertiesFromFile;

//------------------------------------------------------------------------------
// File:         FileConfigClasses.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Classes used for file based configuration.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.21
 */ 
//------------------------------------------------------------------------------
@Configuration
@Import(
  {
    BootstrapPropertiesFromCommandLine.class,
    RepositoryPropertiesFromFile.class,
    DatabasePropertiesFromFile.class,
    ConfigPropertiesFromFile.class    
  }
)
public class FileConfigClasses {
}

package com.easydataservices.db2admintool.repository.agent.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

//------------------------------------------------------------------------------
// File:         AgentDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * DAO parent class for repository agents.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.23
 */ 
//------------------------------------------------------------------------------
@Repository
public abstract class AgentDao
{
  private static final String className = AgentDao.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  protected JdbcTemplate dbJdbcTemplate;
  protected JdbcTemplate repoJdbcTemplate;
  protected SimpleJdbcCall repoSimpleJdbcCall;

  @Autowired
  public void setRepoJdbcTemplate(@Qualifier("repoJdbcTemplate") JdbcTemplate repoJdbcTemplate) {
    final String method = "setJdbcTemplate";
    
    logger.entering(this.className, method, new Object[] {repoJdbcTemplate});
    this.repoJdbcTemplate = repoJdbcTemplate;
    logger.exiting(this.className, method);   
  }

  /**
   * Abstract method to execute the agent activity.
   */
  public abstract void exec();
}

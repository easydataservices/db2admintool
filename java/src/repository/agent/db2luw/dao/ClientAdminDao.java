package com.easydataservices.db2admintool.repository.agent.db2luw.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//------------------------------------------------------------------------------
// File:         ClientAdminDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Repository data access methods for Db2 LUW database clients.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.13
 */ 
//------------------------------------------------------------------------------
@Repository
public class ClientAdminDao
{
  private static final String className = ClientAdminDao.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  @Autowired @Qualifier("repoJdbcTemplate")
  private JdbcTemplate repoJdbcTemplate;
  
  /**
   * Return the repository internal database identifier, matching the specified Db2 for LUW database attributes.
   * @param dbName The database name. This is the value of {@code CURRENT_SERVER}) from the database.
   * @param dbSeed The database seed. This is an interval value used by Db2 to identify the database.
   * @return Repository internal database identifier. If there is no match then a {@code NULL} is returned.
   */
  public Integer getDbId(String dbName, String dbSeed) {
    final String method = "getDbId";
    Integer dbId;

    logger.entering(className, method, new Object[] {dbName, dbSeed});
    String sql = "VALUES ctl_db2luw.common.get_db_id(?, ?)";
    dbId = repoJdbcTemplate.queryForObject(sql, Integer.class, dbName, dbSeed);    
    logger.exiting(className, method, dbId);
    return dbId;
  }
}

package com.easydataservices.db2admintool.database.agent.db2luw;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.database.agent.db2luw.Db2DatabaseIdentity;

//------------------------------------------------------------------------------
// File:         Db2DatabaseIdentityDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Data access methods for checking database identity.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.13
 */ 
//------------------------------------------------------------------------------
@Repository
public class Db2DatabaseIdentityDao
{
  private static final String className = Db2DatabaseIdentityDao.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  @Autowired @Qualifier("dbJdbcTemplate")
  private JdbcTemplate dbJdbcTemplate;
  
  /**
   * Retrieve information to identify DB2 database uniquely.
   */
  public Db2DatabaseIdentity getIdentity() {
    final String method = "getIdentity";

    logger.entering(className, method);
    String sql = "SELECT CURRENT_SERVER AS db_name, value AS db_seed FROM sysibmadm.dbcfg WHERE name = 'db_seed'";
    Db2DatabaseIdentity db2DatabaseIdentity =
      (Db2DatabaseIdentity) dbJdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Db2DatabaseIdentity.class));
    logger.exiting(className, method, db2DatabaseIdentity);
    return db2DatabaseIdentity;
  }
}
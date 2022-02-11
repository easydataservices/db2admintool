package com.easydataservices.db2admintool.repository.agent.db2luw.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.repository.agent.dao.RepositoryAgentDao;

//------------------------------------------------------------------------------
// File:         SnapshotTransformDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Data access methods for transforming Db2 LUW snapshots from staging to
 * repository.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.23
 */ 
//------------------------------------------------------------------------------
@Repository
public class SnapshotTransformDao extends RepositoryAgentDao
{
  private static final String className = SnapshotTransformDao.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  /**
   * Method to capture a database configuration snapshot and persist it in the repository.
   */
  public void exec() {
    final String method = "exec";

    logger.entering(this.className, method);
    transformSnapshot();
    logger.exiting(this.className, method);
  }

  /**
   * Call stored procedure to transform snapshot from staging to repository.
   */
  private void transformSnapshot() {
    final String method = "transformSnapshot";

    logger.exiting(this.className, method);   
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.repoJdbcTemplate)
      .withSchemaName("CTL_DB2LUW")
      .withCatalogName("TRANSFORM")
      .withProcedureName("TRANSFORM_SNDBCF");
    simpleJdbcCall.execute();
    logger.exiting(this.className, method);
  }
}
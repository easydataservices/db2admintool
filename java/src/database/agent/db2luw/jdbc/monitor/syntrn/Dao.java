package com.easydataservices.db2admintool.database.agent.db2luw.jdbc.monitor.syntrn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.database.agent.db2luw.jdbc.monitor.JdbcMonitorSnapshotDao;
import com.easydataservices.db2admintool.util.time.TimeUtils;

//------------------------------------------------------------------------------
// File:         Dao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Data access methods for synthetic transactions.
 * @author jeremy.rickard@easydataservices.com
 * @version 2022.02.06
 */ 
//------------------------------------------------------------------------------
@Repository
public class Dao extends JdbcMonitorSnapshotDao
{
  private static final String className = Dao.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private java.sql.Timestamp snapshotTimestamp;

  /**
   * Execute the synthetic transaction and persist result to the repository.
   */
  public void capture() {
    final String method = "capture";

    logger.entering(className, method);
    snapshotTimestamp = TimeUtils.nowUtc();
    boolean isTransactionOK = execTransaction();
    saveSnapshot(isTransactionOK);
    logger.exiting(className, method);
  }

  /**
   * Execute synthetic transaction (stored procedure). A query timeout of 5 seconds applies.
   * @return {@code true} if the stored procedure completed successfully; otherwise {@code false}.
   */
  private boolean execTransaction() {
    final String method = "execTransaction";
    boolean result = true;

    logger.entering(className, method);
    try {
      logger.logp(Level.FINER, className, method, "Calling synthetic transaction stored procedure...");
      Connection connection = getDbJdbcTemplate().getDataSource().getConnection();
      String sql = "CALL " + getConfigProperties().getProperty("syntrnProcSchema").trim();
      if (getConfigProperties().getProperty("syntrnProcModule") != null) {
        sql = sql + "." + getConfigProperties().getProperty("syntrnProcModule").trim();
      }
      sql = sql + ".SYNTRN()";
      CallableStatement statement = connection.prepareCall(sql);
      statement.setQueryTimeout(5);
      statement.execute();
    }
    catch (SQLException exception) {
      logger.logp(Level.INFO, className, method, "Synthetic transaction failed.\n" + exception.getMessage());
      result = false;
    }
    logger.exiting(className, method);
    return result;
  }

  /**
   * Persist synthetic transaction result to repository.
   * @param isTransactionOK Result from the synthetic transaction stored procedure.
   */  
  private void saveSnapshot(boolean isTransactionOK) {
    final String method = "saveSnapshot";

    logger.entering(className, method, new Object[] {isTransactionOK});
    try {
      logger.logp(Level.FINER, className, method, "Calling stored procedure...");
      Connection connection = getRepoJdbcTemplate().getDataSource().getConnection();
      CallableStatement statement = connection.prepareCall("CALL STG_DB2LUW.SYNTRN.SAVE_SNAPSHOT(?, ?, ?)");
      statement.setObject(1, snapshotTimestamp);
      statement.setObject(2, isTransactionOK);
      statement.setString(3, getAuthToken());
      statement.execute();
    }
    catch (SQLException exception) {
      logger.logp(Level.SEVERE, className, method, exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }  
    logger.exiting(className, method);   
  }
}
package com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.dbcfg;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.database.agent.db2luw.MonitorSnapshotDao;
import com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.dbcfg.Parser;
import com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.dbcfg.SnapshotParameter;
import com.easydataservices.db2admintool.util.exec.LinuxCommand;
import com.easydataservices.db2admintool.util.time.TimeUtils;

//------------------------------------------------------------------------------
// File:         Dao.java
// Licence:      Apache License 2.0
// Description:
/**
 * Data access methods for snapshot of database configuration using the console {@code db2pd -dbcfg} command.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.12.07
 */ 
//------------------------------------------------------------------------------
@Repository
public class Dao extends MonitorSnapshotDao
{
  private static final String className = Dao.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private java.sql.Timestamp snapshotTimestamp;

  /**
   * Capture database configuration snapshot and persist it to the repository.
   */
  public void capture() {
    final String method = "capture";

    logger.entering(className, method);
    snapshotTimestamp = TimeUtils.nowUtc();
    List<SnapshotParameter> parameterList = getSnapshot();
    saveSnapshot(parameterList);
    logger.exiting(className, method);
  }

  /**
   * Retrieve database config snapshot data.
   * @return List of snapshot parameters with values.
   */
  private List<SnapshotParameter> getSnapshot() {
    final String method = "getSnapshot";

    logger.entering(className, method);
    LinuxCommand command = new LinuxCommand();
    Parser parser = new Parser();
    parser.setCommand(command);
    logger.logp(Level.FINE, className, method, "Executing command");
    String commandText = "db2pd -db " + getDb2DatabaseIdentity().getDbName() + " -dbcfg";
    try {
      command.exec(commandText);
    }
    catch (Exception exception) {
      throw new RuntimeException(exception.getMessage());
    }
    List<SnapshotParameter> list = (List<SnapshotParameter>) parser.parse();
    logger.exiting(className, method, list);
    return list;
  }

  /**
   * Persist database config snapshot to repository.
   * @param parameterList List of snapshot parameters with values.
   */
  private void saveSnapshot(List<SnapshotParameter> parameterList) {
    final String method = "saveSnapshot";

    logger.entering(className, method, new Object[] {parameterList});
    try {
      Connection connection = getRepoJdbcTemplate().getDataSource().getConnection();

      logger.logp(Level.FINER, className, method, "Loading parameter details into SQL array variable...");
      Struct[] parameterStructs = new Struct[parameterList.size()];
      for (int i = 0; i < parameterList.size(); i++) {
        SnapshotParameter parameter = parameterList.get(i);
        Object[] structObject = new Object[] {
          parameter.getDbName(),
          parameter.getDbMember(),
          parameter.getDbStatus(),
          parameter.getParameterName(),
          parameter.getValue(),
          parameter.getValueFlags(),
          parameter.getDeferredValue(),
          parameter.getDeferredValueFlags()
        };
        parameterStructs[i] = connection.createStruct("STG_DB2LUW.SNDBCF.PARAMETER", structObject);
      }
      Array parameterArray = connection.createArrayOf("STG_DB2LUW.SNDBCF.PARAMETER", parameterStructs);

      logger.logp(Level.FINER, className, method, "Calling stored procedure...");
      CallableStatement statement = connection.prepareCall("CALL STG_DB2LUW.SNDBCF.SAVE_SNAPSHOT(?, ?, ?)");
      statement.setObject(1, snapshotTimestamp);
      statement.setObject(2, parameterArray);
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
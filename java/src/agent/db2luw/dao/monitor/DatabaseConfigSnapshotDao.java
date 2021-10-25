package com.easydataservices.db2admintool.agent.db2luw.dao.monitor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.agent.db2luw.dao.monitor.SimpleMonitorSnapshotDao;
import com.easydataservices.db2admintool.agent.db2luw.model.monitor.DatabaseConfigSnapshot;

//------------------------------------------------------------------------------
// File:         DatabaseConfigSnapshotDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Data access methods for database config snapshots.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.17
 */ 
//------------------------------------------------------------------------------
@Repository
public class DatabaseConfigSnapshotDao extends SimpleMonitorSnapshotDao
{
  private static final String className = DatabaseConfigSnapshotDao.class.getName();
  private static final Logger logger = Logger.getLogger(className);

  /**
   * Method to capture a database configuration snapshot and persist it in the repository.
   */
  public void capture() {
    final String method = "capture";

    logger.entering(this.className, method);
    List<DatabaseConfigSnapshot> databaseConfigSnapshotList = getSnapshot();
    saveSnapshot(databaseConfigSnapshotList);
    logger.exiting(this.className, method);
  }

  /**
   * Retrieve database config snapshot data.
   */
  private List<DatabaseConfigSnapshot> getSnapshot() {
    final String method = "getSnapshot";

    logger.entering(this.className, method);
    String sql = "SELECT CURRENT_TIMESTAMP - CURRENT_TIMEZONE AS snapshot_timestamp,"
      +  "dbpartitionnum AS db_partition_number, member AS db_member,"
      + " name AS parameter_name, value, value_flags, deferred_value, deferred_value_flags"
      + " FROM sysibmadm.dbcfg";
    List<DatabaseConfigSnapshot> list = this.dbJdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper(DatabaseConfigSnapshot.class));
    Iterator<DatabaseConfigSnapshot> iterator = list.iterator();
    logger.exiting(this.className, method, list);
    return list;
  }

  /**
   * Persist database config snapshot to repository.
   */
  private void saveSnapshot(List<DatabaseConfigSnapshot> databaseConfigSnapshotList) {
    final String method = "saveSnapshot";
    java.sql.Array snapshotArray;

    // Load snapshot into SQL array variable.
    logger.entering(this.className, method, new Object[] {databaseConfigSnapshotList});
    try {
      Connection connection = this.repoJdbcTemplate.getDataSource().getConnection();
      Struct[] structArray = new Struct[databaseConfigSnapshotList.size()];
      for (int i = 0; i < databaseConfigSnapshotList.size(); i++) {
        DatabaseConfigSnapshot databaseConfigSnapshot = databaseConfigSnapshotList.get(i);
        Object[] structObject = new Object[] {
          databaseConfigSnapshot.getSnapshotTimestamp(),
          databaseConfigSnapshot.getDbPartitionNumber(),
          databaseConfigSnapshot.getDbMember(),
          databaseConfigSnapshot.getParameterName(),
          databaseConfigSnapshot.getValue(),
          databaseConfigSnapshot.getValueFlags(),
          databaseConfigSnapshot.getDeferredValue(),
          databaseConfigSnapshot.getDeferredValueFlags()
        };
        structArray[i] = connection.createStruct("CLI_DB2LUW.SNDBCF.SNAPSHOT_ROW", structObject);
      }      
      snapshotArray = connection.createArrayOf("CLI_DB2LUW.SNDBCF.SNAPSHOT_ARRAY", structArray);
    }
    catch (SQLException exception) {
      logger.logp(Level.SEVERE, this.className, method, exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }

    // Call stored procedure.
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.repoJdbcTemplate)
      .withSchemaName("CLI_DB2LUW")
      .withCatalogName("SNDBCF")
      .withProcedureName("SAVE_SNAPSHOT")
      .declareParameters(new SqlParameter("P_DB_NAME", Types.VARCHAR))
      .declareParameters(new SqlParameter("P_DB_SEED", Types.VARCHAR))
      .declareParameters(new SqlParameter("P_AUTH_TOKEN", Types.VARCHAR))
      .declareParameters(new SqlParameter("P_SNAPSHOT_ARRAY", Types.ARRAY));
    Map<String, Object> parameterMap = new HashMap<String, Object>();
    parameterMap.put("P_DB_NAME", this.dbName);
    parameterMap.put("P_DB_SEED", this.dbSeed);
    parameterMap.put("P_AUTH_TOKEN", this.authToken);
    parameterMap.put("P_SNAPSHOT_ARRAY", snapshotArray);
    simpleJdbcCall.execute(new MapSqlParameterSource(parameterMap));
    logger.exiting(this.className, method);   
  }
}
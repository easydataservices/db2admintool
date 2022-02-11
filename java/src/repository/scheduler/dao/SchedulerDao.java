package com.easydataservices.db2admintool.repository.scheduler.dao;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.easydataservices.db2admintool.repository.scheduler.model.Job;
import com.easydataservices.db2admintool.repository.scheduler.model.PollDelay;

//------------------------------------------------------------------------------
// File:         SchedulerDao.java
// Licence:      Apache License 2.0
// Description:  
/**
 * DAO for scheduler.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.31
 */ 
//------------------------------------------------------------------------------
@Repository
public class SchedulerDao
{
  private static final String className = SchedulerDao.class.getName();
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
   * Retrieve poll delay.
   */
  public PollDelay getPollDelay(int dbId, String execType) {
    final String method = "getPollDelay";

    logger.entering(this.className, method, new Object[] {dbId, execType});
    String sql = "SELECT next_poll_timestamp, next_poll_delay_milliseconds FROM TABLE(ctl.schedule.get_poll_delay(?, ?))";
    PollDelay pollDelay = (PollDelay) this.repoJdbcTemplate.queryForObject(sql,
      new Object[] {dbId, execType},
      new int[] {Types.INTEGER, Types.VARCHAR},
      new BeanPropertyRowMapper(PollDelay.class)
      );
    logger.exiting(this.className, method, pollDelay);
    return pollDelay;
  }

  /**
   * Attempt to reserve the next job, and return its details.
   */
  public Job getNextJob(int dbId, String execType) {
    final String method = "getNextJob";
    Job job = new Job();

    logger.entering(this.className, method, new Object[] {dbId, execType});
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.repoJdbcTemplate)
      .withSchemaName("CTL")
      .withCatalogName("SCHEDULE")
      .withProcedureName("GET_NEXT_JOB")
      .declareParameters(new SqlParameter("P_DB_ID", Types.INTEGER))
      .declareParameters(new SqlParameter("P_EXEC_TYPE", Types.VARCHAR))
      .declareParameters(new SqlOutParameter("P_JOB_ID", Types.INTEGER))
      .declareParameters(new SqlOutParameter("P_NEXT_EXEC_TIMESTAMP", Types.TIMESTAMP))
      .declareParameters(new SqlOutParameter("P_RESERVED_ID", Types.INTEGER))
      .declareParameters(new SqlOutParameter("P_EXEC_CLASS", Types.VARCHAR));
    Map<String, Object> parameterMap = new HashMap<String, Object>();
    parameterMap.put("P_DB_ID", dbId);
    parameterMap.put("P_EXEC_TYPE", execType);
    Map<String, Object> result = simpleJdbcCall.execute(new MapSqlParameterSource(parameterMap));
    job.setJobId((Integer) result.get("P_JOB_ID"));
    job.setNextExecTimestamp((Timestamp) result.get("P_NEXT_EXEC_TIMESTAMP"));
    job.setReservedId((Integer) result.get("P_RESERVED_ID"));
    job.setExecClass((String) result.get("P_EXEC_CLASS"));
    logger.exiting(this.className, method, job);
    return job;
  }
}

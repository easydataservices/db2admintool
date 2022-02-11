package com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.dbcfg;

import java.util.Objects;

//------------------------------------------------------------------------------------------------------------------------------
// File:         SnapshotParameter.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Bean for Db2 LUW database configuration parameter snapshot.
 * @author jeremy.rickard@easydataservices.com
 * @version 2022.01.06
 */
//------------------------------------------------------------------------------------------------------------------------------
public class SnapshotParameter
{
  private String dbName;
  private Short dbMember;
  private Short dbStatus;
  private String parameterName;
  private String value;
  private Integer valueFlags;
  private String deferredValue;
  private Integer deferredValueFlags;

  // Getter methods
  /**
   * Return the database name.
   * @return Database name.
   */
  public String getDbName() {
    return dbName;
  }

  /**
   * Return the database member or partition number.
   * @return Database member or partition number.
   */
  public Short getDbMember() {
    return dbMember;
  }

  /**
   * Return the database status.
   * @return {@code 0} if the database is active, {@code 1} if it is an active HADR standby.
   */
  public Short getDbStatus() {
    return dbStatus;
  }

  /**
   * Return the parameter name.
   * @return Database configuration parameter name.
   */
  public String getParameterName() {
    return parameterName;
  }

  /**
   * Return the current parameter value.
   * @return Text representation of current parameter value.
   */
  public String getValue() {
    return value;
  }

  /**
   * Return flags for current parameter value.
   * @return {@code 1} for simple value, {@code 2} for AUTOMATIC value, {@code 4} for COMPUTED.
   */
  public Integer getValueFlags() {
    return valueFlags;
  }

  /**
   * Return deferred parameter value.
   * @return Text representation of deferred parameter value.
   */
  public String getDeferredValue() {
    return deferredValue;
  }

  /**
   * Return flags for deferred parameter value.
   * @return {@code 1} for simple value, {@code 2} for AUTOMATIC value, {@code 4} for COMPUTED.
   */
  public Integer getDeferredValueFlags() {
    return deferredValueFlags;
  }

  // Setter methods
  /**
   * Set the database name.
   * @param dbName Database name.
   */
  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  /**
   * Set the database member or partition number.
   * @param member Database member or partition number.
   */
  public void setDbMember(Short dbMember) {
    this.dbMember = dbMember;
  }
  
/**
   * Set the database status.
   * @param {@code 0} if the database is active, {@code 1} if it is an active HADR standby.
   */
  public void setDbStatus(Short dbStatus) {
    this.dbStatus = dbStatus;
  }

  /**
   * Set the parameter name.
   * @param parameterName Database configuration parameter name.
   */
  public void setParameterName(String parameterName) {
    this.parameterName = parameterName;
  }

  /**
   * Set the current parameter value.
   * @param value Text representation of current parameter value.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Set flags for current parameter value.
   * @param valueFlags {@code 1} for simple value, {@code 2} for AUTOMATIC value, {@code 4} for COMPUTED.
   */
  public void setValueFlags(int valueFlags) {
    this.valueFlags = valueFlags;
  }

  /**
   * Set deferred parameter value.
   * @param deferredValue Text representation of deferred parameter value.
   */
  public void setDeferredValue(String deferredValue) {
    this.deferredValue = deferredValue;
  }
  
  /**
   * Set flags for deferred parameter value.
   * @param deferredValueFlags {@code 1} for simple value, {@code 2} for AUTOMATIC value, {@code 4} for COMPUTED.
   */
  public void setDeferredValueFlags(int deferredValueFlags) {
    this.deferredValueFlags = deferredValueFlags;
  }
}

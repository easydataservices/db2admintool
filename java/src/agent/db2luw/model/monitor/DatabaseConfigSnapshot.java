package com.easydataservices.db2admintool.agent.db2luw.model.monitor;

import java.util.Objects;

//------------------------------------------------------------------------------------------------------------------------------
// File:         DatabaseConfigSnapshot.java
// Licence:      GNU General Public License v3.0
// Description:  
/**
 * Bean for Db2 LUW database configuration snapshot. The snapshot is sourced from SYSIBMADM.DBCFG. 
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.09
 */
//------------------------------------------------------------------------------------------------------------------------------
public class DatabaseConfigSnapshot extends Snapshot
{
  private Short dbPartitionNumber;
  private Short dbMember;
  private String parameterName;
  private String value;
  private String valueFlags;
  private String deferredValue;
  private String deferredValueFlags;

  // Getter methods
  /**
   * Return the database partition number.
   * @return Database partition number.
   */
  public Short getDbPartitionNumber() {
    return this.dbPartitionNumber;
  }

  /**
   * Return the database member.
   * @return Database member.
   */
  public Short getDbMember() {
    return this.dbMember;
  }

  /**
   * Return the parameter name.
   * @return Database configuration parameter name.
   */
  public String getParameterName() {
    return this.parameterName;
  }

  /**
   * Return the current parameter value.
   * @return Text representation of current parameter value.
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Return configuration flags for current parameter value.
   * @return {@code NONE}, {@code AUTOMATIC} or {@code COMPUTED} - see SYSIBMADM.DBCFG.VALUE_FLAGS documentation.
   */
  public String getValueFlags() {
    return this.valueFlags;
  }

  /**
   * Return deferred parameter value.
   * @return Text representation of deferred parameter value.
   */
  public String getDeferredValue() {
    return this.deferredValue;
  }

  /**
   * Return configuration flags for deferred parameter value.
   * @return {@code NONE}, {@code AUTOMATIC} or {@code COMPUTED} - see SYSIBMADM.DBCFG.DEFERRED_VALUE_FLAGS documentation.
   * blank. 
   */
  public String getDeferredValueFlags() {
    return this.deferredValueFlags;
  }

  // Setter methods
  /**
   * Set the parameter name.
   * @param parameterName Database configuration parameter name.
   */
  public void setParameterName(String parameterName) {
    this.parameterName = parameterName;
  }

  /**
   * Set the database partition number.
   * @param dbPartitionNumber Database partition number.
   */
  public void setDbPartitionNumber(Short dbPartitionNumber) {
    this.dbPartitionNumber = dbPartitionNumber;
  }

  /**
   * Set thes database member.
   * @param member Database member.
   */
  public void setDbMember(Short dbMember) {
    this.dbMember = dbMember;
  }

  /**
   * Set the current parameter value.
   * @param value Text representation of current parameter value.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Set configuration flags for current parameter value.
   * @param valueFlags {@code NONE}, {@code AUTOMATIC} or {@code COMPUTED} - see SYSIBMADM.DBCFG.VALUE_FLAGS documentation.
   */
  public void setValueFlags(String valueFlags) {
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
   * Set configuration flags for deferred parameter value.
   * @param deferredValueFlags {@code NONE}, {@code AUTOMATIC} or {@code COMPUTED} - see SYSIBMADM.DBCFG.DEFERRED_VALUE_FLAGS documentation. 
   * compression; otherwiseblank.
   */
  public void setDeferredValueFlags(String deferredValueFlags) {
    this.deferredValueFlags = deferredValueFlags;
  }
  
  // Override methods for Java Collections
  @Override
  public boolean equals(Object object) {
    if (object == null) return false;
    if (!(object instanceof DatabaseConfigSnapshot)) return false;
    DatabaseConfigSnapshot other = (DatabaseConfigSnapshot) object;
    if (!Objects.equals(this.snapshotTimestamp, other.snapshotTimestamp)) return false;
    if (!Objects.equals(this.dbId, other.dbId)) return false;
    if (!Objects.equals(this.dbPartitionNumber, other.dbPartitionNumber)) return false;
    if (!Objects.equals(this.dbMember, other.dbMember)) return false;
    if (!Objects.equals(this.parameterName, other.parameterName)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.snapshotTimestamp, this.dbId, this.dbPartitionNumber, this.dbMember, this.parameterName);
  }
}

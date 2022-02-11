package com.easydataservices.db2admintool.database.agent.db2luw;

import java.sql.Timestamp;

//------------------------------------------------------------------------------------------------------------------------------
// File:         Snapshot.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Parent class for all Db2 monitor snapshots. 
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.09
 */ 
//------------------------------------------------------------------------------------------------------------------------------
public class Snapshot
{
  protected String host;
  protected Timestamp snapshotTimestamp;
  protected int dbId;

  // Getter methods
  /**
   * Return the snapshot host.
   * @return Name of host from which snapshot was captured.
   */
  public String getHost() {
    return host;
  }

  /**
   * Return the snapshot timestamp.
   * @return UTC time that snapshot was captured.
   */
  public Timestamp getSnapshotTimestamp() {
    return snapshotTimestamp;
  }

  /**
   * Return the database identifier.
   * @return Unique identifier for the database. This is an internal value used only by db2admintool.
   */
  @Deprecated public int getDbId() {
    return dbId;
  }

  // Setter methods
  /**
   * Set the snapshot host.
   * @return Name of host from which snapshot was captured.
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Set the snapshot timestamp.
   * @param snapshotTimestamp UTC time that snapshot was captured.
   */
  public void setSnapshotTimestamp(Timestamp snapshotTimestamp) {
    this.snapshotTimestamp = snapshotTimestamp;
  }

  /**
   * Set the database identifier.
   * @param dbId Unique identifier for the database. This is an internal value used only by db2admintool.
   */
  @Deprecated public void setDbId(int dbId) {
    this.dbId = dbId;
  }
}
package com.easydataservices.db2admintool.agent.db2luw.model.monitor;

import java.sql.Timestamp;

//------------------------------------------------------------------------------------------------------------------------------
// File:         Snapshot.java
// Licence:      GNU General Public License v3.0
// Description:  
/**
 * Parent class for all Db2 monitor snapshots. 
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.09
 */ 
//------------------------------------------------------------------------------------------------------------------------------
public class Snapshot
{
  protected Timestamp snapshotTimestamp;
  protected int dbId;

  // Getter methods
  /**
   * Return the snapshot timestamp.
   * @return UTC time that snapshot was captured.
   */
  public Timestamp getSnapshotTimestamp() {
    return this.snapshotTimestamp;
  }

  /**
   * Return the database identifier.
   * @return Unique identifier for the database. This is an internal value used only by db2admintool.
   */
  public int getDbId() {
    return this.dbId;
  }

  // Setter methods
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
  public void setDbId(int dbId) {
    this.dbId = dbId;
  }
}

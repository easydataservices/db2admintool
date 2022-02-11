package com.easydataservices.db2admintool.database.agent.db2luw;

import java.util.Objects;

//------------------------------------------------------------------------------------------------------------------------------
// File:         Db2DatabaseIdentity.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Bean for information to identify a DB2 database uniquely. 
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.13
 */ 
//------------------------------------------------------------------------------------------------------------------------------
public class Db2DatabaseIdentity
{
  private String dbName;
  private String dbSeed;
  private Integer dbId;

  // Getter methods
  /**
   * Return the database name.
   * @return Database name (value of {@code CURRENT_SERVER}).
   */
  public String getDbName() {
    return dbName;
  }

  /**
   * Return the database seed.
   * @return Database seed identifier obtained from database configuration.
   */
  public String getDbSeed() {
    return dbSeed;
  }

  /**
   * Return the repository internal database identifier.
   * @return Repository internal database identifier.
   */
  public Integer getDbId() {
    return dbId;
  }

  // Setter methods
  /**
   * Set the database name.
   * @param dbName Database name (value of {@code CURRENT_SERVER}).
   */
  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  /**
   * Set the database seed.
   * @param dbSeed Database seed identifier obtained from database configuration.
   */
  public void setDbSeed(String dbSeed) {
    this.dbSeed = dbSeed;
  }
  
  /**
   * Set the repository internal database identifier.
   * @param dbId Repository internal database identifier.
   */
  public void setDbId(Integer dbId) {
    this.dbId = dbId;
  }

  // Override methods for Java Collections
  @Override
  public boolean equals(Object object) {
    if (object == null) return false;
    if (!(object instanceof Db2DatabaseIdentity)) return false;
    Db2DatabaseIdentity other = (Db2DatabaseIdentity) object;
    if (!Objects.equals(dbName, other.dbName)) return false;
    if (!Objects.equals(dbSeed, other.dbSeed)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(dbName, dbSeed);
  }
  
  // Override object print method
  @Override
  public String toString() {
    return String.format("Db2DatabaseIdentity [dbName=" + dbName + ", dbSeed=" + dbSeed + "]");
  }
}
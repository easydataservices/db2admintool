package com.easydataservices.db2admintool.repository.scheduler.model;

import java.sql.Timestamp;
import java.util.Objects;

//------------------------------------------------------------------------------------------------------------------------------
// File:         Job.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Bean for repository scheduler job.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.06
 */
//------------------------------------------------------------------------------------------------------------------------------
public class Job
{
  private Integer jobId;
  private Timestamp nextExecTimestamp;
  private Integer reservedId;
  private String execClass;

  // Getter methods
  /**
   * Return the job identifier.
   * @return Job identifier.
   */
  public Integer getJobId() {
    return this.jobId;
  }

  /**
   * Return the next execution timestamp.
   * @return Job scheduled UTC execution timestamp.
   */
  public Timestamp getNextExecTimestamp() {
    return this.nextExecTimestamp;
  }

  /**
   * Return the reserved identifier.
   * @return Reserved identifier.
   */
  public Integer getReservedId() {
    return this.reservedId;
  }

  /**
   * Return the execution class name.
   * @return Execution class name.
   */
  public String getExecClass() {
    return this.execClass;
  }

  // Setter methods
  /**
   * Set the job identifier.
   * @param jobId Job identifier.
   */
  public void setJobId(Integer jobId) {
    this.jobId = jobId;
  }

  /**
   * Set the next execution timestamp.
   * @param nextExecTimestamp Job scheduled UTC execution timestamp.
   */
  public void setNextExecTimestamp(Timestamp nextExecTimestamp) {
    this.nextExecTimestamp = nextExecTimestamp;
  }

  /**
   * Set the reserved identifier.
   * @param reservedId Reserved identifier.
   */
  public void setReservedId(Integer reservedId) {
    this.reservedId = reservedId;
  }

  /**
   * Set the execution class name.
   * @param execClass Execution class name.
   */
  public void setExecClass(String execClass) {
    this.execClass = execClass;
  }
  
  // Override methods for Java Collections
  @Override
  public boolean equals(Object object) {
    if (object == null) return false;
    if (!(object instanceof Job)) return false;
    Job other = (Job) object;
    if (!Objects.equals(this.jobId, other.jobId)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.jobId);
  }
  
  // Override object print method
  @Override
  public String toString() {
    return String.format("Job [jobId=" + jobId + ", nextExecTimestamp=" + nextExecTimestamp + ", execClass=" + execClass + "]");
  }
}

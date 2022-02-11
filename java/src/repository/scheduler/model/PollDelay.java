package com.easydataservices.db2admintool.repository.scheduler.model;

import java.sql.Timestamp;
import java.util.Objects;

//------------------------------------------------------------------------------------------------------------------------------
// File:         PollDelay.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Bean for repository polling delay details. 
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.06
 */
//------------------------------------------------------------------------------------------------------------------------------
public class PollDelay
{
  private Timestamp nextPollTimestamp;
  private Integer nextPollDelayMilliseconds;

  // Getter methods
  /**
   * Return the next poll timestamp.
   * @return UTC time of next poll.
   */
  public Timestamp getNextPollTimestamp() {
    return this.nextPollTimestamp;
  }

  /**
   * Return the delay time in milliseconds to the next poll.
   * @return Delay time in milliseconds.
   */
  public Integer getNextPollDelayMilliseconds() {
    return this.nextPollDelayMilliseconds;
  }

  // Setter methods
  /**
   * Set the next poll timestamp.
   * @param nextPollTimestamp UTC time of next poll.
   */
  public void setNextPollTimestamp(Timestamp nextPollTimestamp) {
    this.nextPollTimestamp = nextPollTimestamp;
  }

  /**
   * Set the delay time in milliseconds to the next poll.
   * @param nextPollDelayMilliseconds Delay time in millisecond.
   */
  public void setNextPollDelayMilliseconds(Integer nextPollDelayMilliseconds) {
    this.nextPollDelayMilliseconds = nextPollDelayMilliseconds;
  }

  // Override methods for Java Collections
  @Override
  public boolean equals(Object object) {
    if (object == null) return false;
    if (!(object instanceof PollDelay)) return false;
    PollDelay other = (PollDelay) object;
    if (!Objects.equals(this.nextPollTimestamp, other.nextPollTimestamp)) return false;
    if (!Objects.equals(this.nextPollDelayMilliseconds, other.nextPollDelayMilliseconds)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.nextPollTimestamp, this.nextPollDelayMilliseconds);
  }
  
  // Override object print method
  @Override
  public String toString() {
    return String.format("PollDelay [nextPollTimestamp=" + nextPollTimestamp + ", nextPollDelayMilliseconds=" + nextPollDelayMilliseconds + "]");
  }
}

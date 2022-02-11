package com.easydataservices.db2admintool.util.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

//------------------------------------------------------------------------------
// File:         TimeUtils.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Class for miscellaneous time methods.
 * @author jeremy.rickard@easydataservices.com
 * @version 2022.02.10
 */ 
//------------------------------------------------------------------------------
public class TimeUtils {
  /**
   * Return current UTC time as a Java SQL timestamp.
   * @return Current UTC time.
   */
  public static java.sql.Timestamp nowUtc() {
    LocalDateTime localDateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());      
    ZonedDateTime utc = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
    return java.sql.Timestamp.valueOf(utc.toLocalDateTime());
  }
}

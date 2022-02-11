package com.easydataservices.db2admintool.database.agent.db2luw.console.monitor;

import java.util.Properties;

//------------------------------------------------------------------------------
// File:         Db2pdParserProperties.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Constants for parsing header output from {@code db2pd}.
 * @author jeremy.rickard@easydataservices.com
 * @version 2022.01.03
 */ 
//------------------------------------------------------------------------------
public class Db2pdParserProperties {
  private Properties db2pdRegexProperties = new Properties();

  /**
   * Constructor.
   */
  public Db2pdParserProperties() {
    setDb2pdRegexProperties();
  }
  
  /**
   * Return regex properties for db2pd.
   */
  public Properties getDb2pdRegexProperties() {
    return db2pdRegexProperties;
  }

  /**
   * Set regex properties for db2pd.
   */
  protected void setDb2pdRegexProperties() {
    db2pdRegexProperties.setProperty("BLANK_LINE", "\\s*$");
    db2pdRegexProperties.setProperty("LABEL_DASHES", "\\s--\\s");
    db2pdRegexProperties.setProperty("LABEL_MEMBER", "Database Member\\s+");
    db2pdRegexProperties.setProperty("LABEL_DATABASE", "\\s-- Database\\s");
    db2pdRegexProperties.setProperty("LABEL_UPTIME", "\\s-- Up\\s");
    db2pdRegexProperties.setProperty("LABEL_DATE", "\\s-- Date\\s");
  }
}

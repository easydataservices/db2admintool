package com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.dbcfg;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.dbcfg.ParserProperties;
import com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.dbcfg.SnapshotParameter;
import com.easydataservices.db2admintool.util.parser.CommandOutputParser;
import com.easydataservices.db2admintool.util.parser.LineParser;
import com.easydataservices.db2admintool.util.parser.ParsingException;

//------------------------------------------------------------------------------
// File:         Parser.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Class for parsing output from {@code db2pd -dbcfg}.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.12.12
 */ 
//------------------------------------------------------------------------------
public class Parser extends CommandOutputParser {
  private static final String className = Parser.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private LineParser lineParser;
  private int column2StartIndex;
  private int column3StartIndex;
  
  protected ParserProperties parserProperties = new ParserProperties();

  /**
   * Parse command output.
   */
  @Override
  protected List<SnapshotParameter> parseAll() {
    final String method = "parseAll";

    logger.entering(className, method);
    List<SnapshotParameter> snapshotList = new ArrayList<SnapshotParameter>();
    SnapshotParameter snapshotTemplate = null;
    short step = 1;
    parsing:
    while (getCommand().getProcess().isAlive() || getCommand().getBufferLineCount() > 0) {
      String line = readLine();
      if (line != null) {
        lineParser = new LineParser(line);
        switch (step) {
          case 1: // Blank line
            if (!lineParser.isMatch(parserProperties.getDb2pdRegexProperties().getProperty("BLANK_LINE"))) {
              throwParsingException("Missing blank line");
            }
            step = 2;
            break;
          case 2: // Header line
            snapshotTemplate = new SnapshotParameter();
            parseHeader(snapshotTemplate);
            step = 3;
            break;
          case 3: // Blank line
            if (!lineParser.isMatch(parserProperties.getDb2pdRegexProperties().getProperty("BLANK_LINE"))) {
              throwParsingException("Missing blank line");
            }
            step = 4;
            break;
          case 4: // Database configuration settings header
            if (!lineParser.isMatch(parserProperties.getSectionRegexProperties().getProperty("SECTION"))) {
              throwParsingException("Missing database configuration settings header");
            }
            step = 5;
            break;
          case 5: // Column headings
            if (!lineParser.isMatch(parserProperties.getSectionRegexProperties().getProperty("COLS"))) {
              throwParsingException("Missing column headings");
            }
            if (!lineParser.skipText(parserProperties.getSectionRegexProperties().getProperty("COL_DESCRIPTION"))) {
              throwParsingException("Failed to skip Description column heading");
            }
            column2StartIndex = lineParser.getPosition();                
            if (!lineParser.skipText(parserProperties.getSectionRegexProperties().getProperty("COL_MEMORY_VALUE"))) {
              throwParsingException("Failed to skip Memory Value column heading");
            }
            column3StartIndex = lineParser.getPosition();                
            step = 6;
            break;
          case 6: // Parameter entries
            parseConfigurationParameterList(snapshotTemplate, snapshotList);
            break;
          default:
            break parsing;
        }
      }  
    }
    logger.exiting(className, method);
    return snapshotList;
  }

  /**
   * Parse configuration parameter entries.
   */
  protected void parseConfigurationParameterList(SnapshotParameter snapshotTemplate, List<SnapshotParameter> snapshotList) {
    final String method = "parseConfigurationParameterList";

    logger.entering(className, method);
    short step = 1;
    parsing:
    while (getCommand().getProcess().isAlive() || getCommand().getBufferLineCount() > 0) {
      String line = readLine();
      if (line != null) {
        lineParser = new LineParser(line);
        
        // Exit upon blank line
        if (lineParser.isMatch(parserProperties.getDb2pdRegexProperties().getProperty("BLANK_LINE"))) {
          break parsing;
        }
        
        String lookupKey = line.substring(0, column2StartIndex - 1).trim();
        
        // Attempt to match lookup key to short value parameter.        
        String mappedValue = parserProperties.getParameterProperties().getProperty(lookupKey);
        if (mappedValue != null) {
          try {
            SnapshotParameter snapshotParameter = createSnapshotParameterFromTemplate(snapshotTemplate);
            snapshotParameter.setParameterName(mappedValue);
            parseValue(line.substring(column2StartIndex, column3StartIndex - 1), snapshotParameter);
            parseDeferredValue(line.substring(column3StartIndex), snapshotParameter);
            snapshotList.add(snapshotParameter);            
          }
          catch (Exception exception) {
            throwParsingException("Unexpected condition retrieving short value parameter:\n" + exception.getMessage());
          }
        }
        
        // Otherwise attempt to match lookup key to long value memory parameter.        
        if (mappedValue == null && lookupKey.endsWith(getMemorySuffix())) {
          int endIndex = lookupKey.length() - getMemorySuffix().length() - 1;
          mappedValue = parserProperties.getParameterProperties().getProperty(lookupKey.substring(0, endIndex));
          if (mappedValue != null) {
            SnapshotParameter snapshotParameter = createSnapshotParameterFromTemplate(snapshotTemplate);
            snapshotParameter.setParameterName(mappedValue);
            parseValue(line.substring(column2StartIndex), snapshotParameter);
            snapshotList.add(snapshotParameter);            
          }
        }

        // Otherwise attempt to match lookup key to long value disk parameter.        
        if (mappedValue == null && lookupKey.endsWith(getDiskSuffix())) {
          int endIndex = lookupKey.length() - getDiskSuffix().length() - 1;
          mappedValue = parserProperties.getParameterProperties().getProperty(lookupKey.substring(0, endIndex));
          if (mappedValue != null) {
            int entryCount = snapshotList.size();
            if (entryCount == 0) {
              throwParsingException("Unexpected long value disk parameter");
            }
            SnapshotParameter snapshotParameter = snapshotList.get(entryCount - 1);
            if (snapshotParameter.getParameterName().equals(mappedValue)) {
              parseDeferredValue(line.substring(column2StartIndex), snapshotParameter);
            }
            else {
              throwParsingException("Unmatched long value disk parameter");
            }
          }
        }

        // Otherwise attempt to match oversized lookup key.
        if (mappedValue == null) {
          for (String propertyName : parserProperties.getParameterProperties().stringPropertyNames()) {
            if (propertyName.length() >= column2StartIndex && line.length() >= column2StartIndex) {
              if (line.startsWith(propertyName)) {
                mappedValue = parserProperties.getParameterProperties().getProperty(propertyName);
                if (mappedValue != null) {
                  SnapshotParameter snapshotParameter = createSnapshotParameterFromTemplate(snapshotTemplate);
                  snapshotParameter.setParameterName(mappedValue);
                  int displacement = propertyName.length() - column2StartIndex + 1;
                  parseValue(line.substring(column2StartIndex + displacement, column3StartIndex + displacement - 1), snapshotParameter);
                  parseDeferredValue(line.substring(column3StartIndex + displacement), snapshotParameter);
                  snapshotList.add(snapshotParameter);
                }
              }
            }
          }
        }

        // Handle unmatched line.
        if (mappedValue == null) {
          logger.logp(Level.INFO, className, method, "Ignored unmatched lookup key [" + lookupKey + "]");
        }
      }
    }
    logger.exiting(className, method);
  }

  /**
   * Parse db2pd header, and set corresponding header fields (only) of supplied {@code snapshotTemplate}.
   * @param snapshotTemplate Initialised {@link SnapshotParameter} object. 
   */
  protected void parseHeader(SnapshotParameter snapshotTemplate) {
    final String method = "parseHeader";

    logger.entering(className, method);
    // Parse database member.
    if (!lineParser.skipText(parserProperties.getDb2pdRegexProperties().getProperty("LABEL_MEMBER"))) {
      throwParsingException("Failed to skip Database Member label");
    }
    try {
      snapshotTemplate.setDbMember(Short.parseShort(lineParser.getTextBefore(parserProperties.getDb2pdRegexProperties().getProperty("LABEL_DATABASE"))));
      logger.logp(Level.INFO, className, method, "snapshotTemplate.getDbMember(): " + snapshotTemplate.getDbMember());
    }
    catch (Exception exception) {
      throwParsingException("Failed to parse database member");
    }
    
    // Parse database name.
    snapshotTemplate.setDbName(lineParser.getTextBefore(parserProperties.getDb2pdRegexProperties().getProperty("LABEL_DASHES")));
    if (isEmptyString(snapshotTemplate.getDbName())) {
      throwParsingException("Failed to parse database name");
    }

    // Parse database status.
    String status = lineParser.getTextBefore(parserProperties.getDb2pdRegexProperties().getProperty("LABEL_UPTIME"));
    switch (status) {
      case "Active":
        snapshotTemplate.setDbStatus((short) 0);
        break;
      case "Standby":
        snapshotTemplate.setDbStatus((short) 1);
        break;
      default:
        throwParsingException("Failed to parse or recognise database status");
    }

    // Parse (and skip) database member uptime and snapshot timestamp.
    lineParser.getTextBefore("$");

    logger.exiting(className, method);
  }

  /**
   * Parse value (memory value) and set related properties in the supplied snapshot parameter object.
   * @param text Raw value text to parse.
   * @param snapshotParameter Instantiated snapshot parameter object.
   */
  protected void parseValue(String text, SnapshotParameter snapshotParameter) {
    String work = text.trim();
    int length = work.length();
    if (work.startsWith("AUTOMATIC") && work.endsWith(")")) {
      snapshotParameter.setValue(work.substring(9, length - 1));
      snapshotParameter.setValueFlags(2);
    }
    else if (work.startsWith("COMPUTED") && work.endsWith(")")) {
      snapshotParameter.setValue(work.substring(8, length - 1));
      snapshotParameter.setValueFlags(4);
    }
    else {
      snapshotParameter.setValue(work);
      snapshotParameter.setValueFlags(1);
    }
  }

  /**
   * Parse deferred value (disk value) and set related properties in the supplied snapshot parameter object.
   * @param text Raw value text to parse.
   * @param snapshotParameter Instantiated snapshot parameter object.
   */
  protected void parseDeferredValue(String text, SnapshotParameter snapshotParameter) {
    String work = text.trim();
    int length = work.length();
    if (work.startsWith("AUTOMATIC") && work.endsWith(")")) {
      snapshotParameter.setDeferredValue(work.substring(9, length - 1));
      snapshotParameter.setDeferredValueFlags(2);
    }
    else if (work.startsWith("COMPUTED") && work.endsWith(")")) {
      snapshotParameter.setDeferredValue(work.substring(8, length - 1));
      snapshotParameter.setDeferredValueFlags(4);
    }
    else {
      snapshotParameter.setDeferredValue(work);
      snapshotParameter.setDeferredValueFlags(1);
    }
  }

  /**
   * Return tag used to identify long parameter disk values. By default this is {@code (disk)}.
   * @return Tag associated with disk values.
   */
  protected String getDiskSuffix() {
    return "(disk)";
  }

  /**
   * Return tag used to identify long parameter memory values.  By default this is {@code (memory)}.
   * @return Tag associated with memory values.
   */
  protected String getMemorySuffix() {
    return "(memory)";
  }

  /**
   * Initialise all snapshot parameter header fields from template.
   * @param snapshotTemplate Snapshot template for header fields.
   * @return New SnapshotParameter object initialised from template.
   */
  private SnapshotParameter createSnapshotParameterFromTemplate(SnapshotParameter snapshotTemplate) {
    SnapshotParameter snapshotParameter = new SnapshotParameter();
    snapshotParameter.setDbName(snapshotTemplate.getDbName());
    snapshotParameter.setDbMember(snapshotTemplate.getDbMember());
    snapshotParameter.setDbStatus(snapshotTemplate.getDbStatus());
    return snapshotParameter;
  }

  /**
   * Throw parsing exception.
   * @param message Error description.
   */
  private void throwParsingException(String message) {
    throw new ParsingException(getLineNumber(), lineParser.getPosition(), message + "\nText: " + lineParser.getRemainingLine());
  }

  /**
   * Check for null or empty string.
   * @param text Input text.
   * @return {@code true} if the input text is a null or empty string; otherwise {@code false}.
   */
  private boolean isEmptyString(String text) {
    return text == null || text.isEmpty();
  }
}

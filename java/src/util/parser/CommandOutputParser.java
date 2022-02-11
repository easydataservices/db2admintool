package com.easydataservices.db2admintool.util.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.easydataservices.db2admintool.util.exec.Command;

//------------------------------------------------------------------------------
// File:         CommandOutputParser.java
// Licence:      Apache License 2.0
// Description:
/**
 * Abstract base class for parsing command output. Subclasses extended this class to provide parsing support for the output 
 * of specific commands.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.12.11
 */
//------------------------------------------------------------------------------
public abstract class CommandOutputParser {
  private static final String className = CommandOutputParser.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private Command command;
  private int lineNumber = 0;

  /**
   * Parse all command output. Invokes method {@link #parseAll}, implemented by the subclass, and returns results from that
   * method. If the stream is not fully consumed by {@code parseAll} then {@code parse} reads and discard all remaining data.
   */
  public List<?> parse() {
    final String method = "parse";

    logger.entering(className, method);
    List<?> parsedObjectList = parseAll();
    while (getCommand().getProcess().isAlive() || getCommand().getBufferLineCount() > 0) {
      readLine();
    }
    logger.exiting(className, method, parsedObjectList);
    return parsedObjectList;
  } 

  /**
   * Set the executing command, from which output will be parsed.
   * @param command Executing command. The class is not responsible for calling {@link Command.exec}.
   */
  public void setCommand(Command command) {
    final String method = "setCommand";

    logger.entering(className, method, new Object[] {command});
    this.command = command;
    logger.exiting(className, method);
  }

  /**
   * Return the executing command.
   * @return Executing command. The class is not responsible for calling {@link Command.exec}.
   */
  public Command getCommand() {
    final String method = "getCommand";

    logger.entering(className, method);
    logger.exiting(className, method, command);
    return command;
  }
  
  /**
   * Parse all command output.
   */
  protected abstract List<?> parseAll();

  /**
   * Return a line of input.
   * @return String containing one line of text; {@code null} if the end of stream has been reached or if a stream error occurs. 
   */
  protected String readLine() {
    final String method = "readLine";

    logger.entering(className, method);
    String line;

    line = command.getLine();
    if (line != null) {
      lineNumber = lineNumber + 1;
    }
    logger.exiting(className, method, line);
    return line;
  }

  /**
   * Return the current line number.
   * @return Line number, or zero if no line has been read. 
   */
  protected int getLineNumber() {
    final String method = "getLineNumber";

    logger.entering(className, method);
    logger.exiting(className, method, lineNumber);
    return lineNumber;
  }
}

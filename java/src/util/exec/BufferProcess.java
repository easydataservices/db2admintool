package com.easydataservices.db2admintool.util.exec;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//------------------------------------------------------------------------------
// File:         BufferProcess.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Class for buffering console command output.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.28
 */ 
//------------------------------------------------------------------------------
public class BufferProcess implements Runnable {
  private static final String className = BufferProcess.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private List<String> lineBuffer = Collections.synchronizedList(new ArrayList<String>());
  private InputStream inputStream;

  public BufferProcess(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  @Override
  /**
   * Consumes the entire input stream.
   */  
  public void run() {
    final String method = "run";

    logger.entering(className, method);
    new BufferedReader(new InputStreamReader(inputStream)).lines().forEachOrdered(this::writeLine);
    logger.exiting(className, method);
  }

  /**
   * Retrieve the number of lines in the buffer.
   * @return Number of lines.
   */
  public int getSize() {
    final String method = "getSize";

    logger.entering(className, method);
    int size = lineBuffer.size();
    logger.exiting(className, method, size);
    return size;
  }

  /**
   * Retrieve the oldest line (and remove it) from the buffer.
   * @return Retrieved line of text; {@code null} if there are no lines in buffer.
   */
  public String readLine() {
    final String method = "readLine";
    String line = null;

    logger.entering(className, method);
    if (lineBuffer.size() > 0) {
      line = lineBuffer.remove(0);
    }
    logger.exiting(className, method, line);
    return line;
  }

  /**
   * Write a line to the buffer.
   * @param text Line of text to add.
   */
  private void writeLine(String text) {
    final String method = "writeLine";

    logger.entering(className, method, new Object[] {text});
    lineBuffer.add(text);
    logger.exiting(className, method);
  }
}

package com.easydataservices.db2admintool.util.exec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.easydataservices.db2admintool.util.exec.BufferProcess;

//------------------------------------------------------------------------------
// File:         Command.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Abstract base class for executing a system process.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.28
 */ 
//------------------------------------------------------------------------------
public abstract class Command {
  private static final String className = Command.class.getName();
  private static final Logger logger = Logger.getLogger(className);
  private BufferProcess bufferProcess;
  private Process process;
  private Integer exitCode;

  /**
   * Return operating system program and initial arguments.
   */
  protected abstract String[] getShell();

  /**
   * Execute process
   * @param commandArguments Zero or more arguments.
   */
  public void exec(String... commandArguments) throws Exception {
    final String method = "exec";

    logger.entering(className, method);
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.redirectErrorStream(true);
    List<String> arguments = new ArrayList<String>();
    for (String s : getShell()) {
      arguments.add(s);
    }
    for (String s : commandArguments) {
      arguments.add(s);
    }
    processBuilder.command(arguments);
    process = processBuilder.start();
    bufferProcess = new BufferProcess(process.getInputStream());
    new Thread(bufferProcess).start();
    exitCode = process.waitFor();
    logger.exiting(className, method);
  }

  /**
   * Return subprocess.
   * @return Subprocess running the command; {@code null} if execution has not started.
   */  
  public Process getProcess() {
    final String method = "getProcess";

    logger.entering(className, method);
    logger.exiting(className, method, process);
    return process;
  }

  /**
   * Return exit code of previous execution.
   * @return Exit code of previous execution; {@code null} if an execution has not been completed.
   */  
  public Integer getExitCode() {
    final String method = "getExitCode";

    logger.entering(className, method);
    logger.exiting(className, method, exitCode);
    return exitCode;
  }

  /**
   * Return the number of lines in the buffer.
   * @return Number of lines left in buffer.
   */
  public int getBufferLineCount() {
    final String method = "getBufferLineCount";

    logger.entering(className, method);
    int count = bufferProcess.getSize();
    logger.exiting(className, method, count);
    return count;
  }

  /**
   * Return a line of output.
   * @return Retrieved line of text; {@code null} if there are no lines left in buffer.
   */
  public String getLine() {
    final String method = "getLine";

    logger.entering(className, method);
    String line = bufferProcess.readLine();
    logger.exiting(className, method, line);
    return line;
  }
}

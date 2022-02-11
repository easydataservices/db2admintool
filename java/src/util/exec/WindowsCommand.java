package com.easydataservices.db2admintool.util.exec;

import com.easydataservices.db2admintool.util.exec.Command;

//------------------------------------------------------------------------------
// File:         WindowsCommand.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Class for executing a Windows system process.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.11.28
 */ 
//------------------------------------------------------------------------------
public class WindowsCommand extends Command {
  /**
   * Return operating system program and initial arguments.
   */
  protected String[] getShell() {
    return new String[] {"cmd.exe", "/c"};
  }
}

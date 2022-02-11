package com.easydataservices.db2admintool.util.parser;

//------------------------------------------------------------------------------
// File:         ParsingException.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Class for exceptions that occur while parsing text.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.12.30
 */ 
//------------------------------------------------------------------------------
public class ParsingException extends RuntimeException {
  // Constructor
  /**
   * Constructor.
   * @param lineNumber The line number at which the parsing exception has occurred.
   * @param position The character offset at which the parsing exception has occurred.
   * @param message Custom message describing the parsing error.
   */
  public ParsingException(int lineNumber, int position, String message) {
    super("Parsing exception occurred at line " + lineNumber + " and character " + position + ".\nMessage: " + message);
  }
}

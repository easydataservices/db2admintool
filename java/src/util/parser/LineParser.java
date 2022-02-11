package com.easydataservices.db2admintool.util.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//------------------------------------------------------------------------------
// File:         LineParser.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Class for parsing a single line of text.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.12.27
 */ 
//------------------------------------------------------------------------------
public class LineParser {
  private int matchEndIndex = 0;
  private String originalLine;
  private String line;
    
  // Constructor
  /**
   * Constructor.
   * @param line Line of text to parse.
   */
  public LineParser(String line) {
    originalLine = line;
    this.line = line;
  }

  // Parser methods
  /**
   * Return the current position.
   * @return Current position.
   */
  public int getPosition() {
    int result = originalLine.length() - line.length();
    return result;
  }

  /**
   * Return the original line.
   * @return Original line of text.
   */
  public String getOriginalLine() {
    return originalLine;
  }
  
  /**
   * Return the remaining line.
   * @return Remaining line of text (from current position).
   */
  public String getRemainingLine() {
    return line;
  }
  
  /**
   * Skip the specified text. The current position is only advanced if a match is found for the specified text.
   * @param regex Regular expression that defines the text to skip.
   * @return {@code true} if a matching token was skipped; otherwise {@code false}.
   */
  public boolean skipText(String regex) {
    boolean result = false;
    String[] tokens = line.split(regex, 2);
    if (tokens.length == 2) {
      line = tokens[1];
      result = true;
    }
    return result;
  }

  /**
   * Get the text before the specified delimiter. The current position is only advanced if a match is found.
   * @param regex Regular expression defining the delimiter.
   * @return Matching text if found; otherwise {@code null}.
   */
  public String getTextBefore(String regex) {
    String text = null;
    String[] tokens = line.split(regex, 2);
    if (tokens.length == 2) {
      text = tokens[0];
      line = tokens[1];
    }
    return text;
  }
  
  /**
   * Check whether text starting at the current position matches the specified regular expression. The current position is not
   * advanced.
   * @param regex Regular expression that defines a string match.
   * @return {@code true} if the text matches; otherwise {@code false}.
   */
  public boolean isMatch(String regex) {
    boolean result = false;
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);
    matchEndIndex = 0;
    if (matcher.find() && matcher.start() == 0) {
      matchEndIndex = matcher.end();
      result = true;
    }
    return result;
  }
  
  /**
   * Returns offset after the last character matched by the previous {@link #isMatch} call.
   * @return Offset after the last character matched; a {@code 0} is returned if there was no match.
   */
  public int getMatchEndIndex() {
    return matchEndIndex;
  }
}

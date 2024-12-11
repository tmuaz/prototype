package org.sepp;

/** Helper */
public class Helper {
  public static String sanitizeString(String str) {
    // remove string names that can't be saved as files
    str = str.replaceAll("[\\\\/:*<>|\"?]", "");
    return str;
  }
}

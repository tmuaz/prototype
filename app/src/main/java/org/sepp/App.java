package org.sepp;

public class App {

  public static void main(String[] args) {
    if (args.length == 0) {
      GUI.main(args);
    } else {
      Cli.runCli(args);
    }
  }
}

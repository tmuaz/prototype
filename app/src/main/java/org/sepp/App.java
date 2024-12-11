package org.sepp;

public class App {

  public static void main(String[] args) {
    // to nahir: do check to run cli/gui
    if (args.length == 0) {
      GUI.main(args);
    } else {
      Cli.runCli(args);
    }
  }
}

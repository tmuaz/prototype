package org.sepp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

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

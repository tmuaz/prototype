package org.sepp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class App {

  public static void main(String[] args) {
    // this is the config we are goin to work with
    Config config = null;
    // get our Cli singleton
    Cli cli = Cli.getInstance();
    // flag to check if we need to save our config
    Boolean saveConfig = false;

    try {
      CommandLine line = cli.parse(args);
      // basic usage guide
      if (line.hasOption("help")) {
        cli.printHelp();
        return;
      }

      // attempt to get our config
      if (line.hasOption("load")) {
        File configFile = new File(line.getOptionValue("load"));
        try {
          config = Config.loadFromFile(configFile);
        } catch (Exception e) {
          System.err.println("Could not load config.\nError: " + e.getMessage());
          System.exit(1);
        }
      } else if (line.hasOption("create")) {
        config = new Config();
        saveConfig = true;
      } else {
        // we need a configuration, otherwise nothing can be done
        System.err.println("No config provided, see --help");
        System.exit(1);
      }

      // adding tasks
      if (line.hasOption("add-task")) {
        String[] taskstr = line.getOptionValue("add-task").split(" ");

        // task needs 2 arguments
        if (taskstr.length < 2) {
          System.err.println("Invalid task, see --help");
          System.exit(1);
        }

        // Verify that file exists and is a file
        File shfile = new File(taskstr[1]);
        if (!shfile.isFile()) {
          System.err.println("Invalid shell file");
          System.exit(1);
        }

        // try to read contents of file at path
        List<String> sh;
        try {
          Path path = Paths.get(taskstr[1]);
          sh = Files.readAllLines(path);
        } catch (Exception e) {
          System.err.println("Failed to read " + taskstr[1] + " \nError: " + e.getMessage());
          System.exit(1);
        }

        // TODO: create and add our task

        // ensure config is updated
        saveConfig = true;
      }

    } catch (ParseException e) {
      System.err.println("Parsing failed.  Reason: " + e.getMessage());
      System.exit(1);
    }

    if (saveConfig) {
      try {
        config.storeConfig("path");
      } catch (IOException e) {
        System.err.println("Failed to save config.\nError: " + e.getMessage());
      }
    }
  }
}

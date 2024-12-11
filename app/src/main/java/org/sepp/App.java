package org.sepp;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class App {
  private static Logger LOGGER = Logger.getLogger("App");

  public static void main(String[] args) {

    AppDirs ad = AppDirsFactory.getInstance();
    String path = ad.getUserConfigDir("automarker", null, null);

    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-mm-yyyy_hh-mm-ss"));

    var file = new File(path + "/logs/" + date + ".txt");

    try {
      if ((file.getParentFile().exists() || file.getParentFile().mkdir()) && file.createNewFile()) {
        LOGGER.addHandler(new FileHandler(file.getAbsolutePath()));
      } else {
        throw new RuntimeException("Failed to create directory " + file.toString());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (args.length == 0) {
      LOGGER.addHandler(new ConsoleHandler());
      GUI.main(args);
    } else {
      Cli.runCli(args);
    }
  }
}

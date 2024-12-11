package org.sepp;

import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Context {
  Config config = null;
  File runDirectory = null;
  Boolean saveConfig = false;
  File selected = null;

  public Context() {}

  public List<String> getConfigFileNames() {
    File[] files = Config.listConfigs();
    return Arrays.stream(files)
        .map(
            f -> {
              String name = f.getName();
              return name.substring(0, name.length() - 5);
            })
        .toList();
  }

  // returns empty list if unsuccessful
  public List<File> getProjects() {
    File[] files = runDirectory.listFiles(File::isDirectory);
    if (files != null) {
      return Arrays.stream(files).toList();
    } else {
      return new ArrayList<>();
    }
  }
}

package org.sepp;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Context {
  Config config = null;
  File runDirectory = null;
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
}

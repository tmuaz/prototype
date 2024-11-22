package org.sepp;

import java.io.File;

public class Task {
  public enum TaskType {
    COMPILE,
    CUSTOM
  }

  public TaskType type;
  public String script;

  public Task(TaskType type, String script) {
    this.type = type;
    this.script = script;
  }

  public String run(File folder) {
    throw new RuntimeException("Not implemented");
  }
}

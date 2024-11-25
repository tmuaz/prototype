package org.sepp;

import java.io.File;
import java.util.List;

public class Task {
  public enum TaskType {
    COMPILE,
    CUSTOM
  }

  public String name;
  public TaskType type;
  public List<String> script;

  public Task(TaskType type, List<String> script) {
    this.name = "Unnamed task";
    this.type = type;
    this.script = script;
  }

  public Task(String name, TaskType type, List<String> script) {
    this.name = name;
    this.type = type;
    this.script = script;
  }

  public String run(File folder) {
    throw new RuntimeException("Not implemented");
  }

  public static Task.TaskType parseType(String input) {
    input = input.toLowerCase();
    if (input.startsWith("co")) {
      return TaskType.COMPILE;
    }
    return TaskType.CUSTOM;
  }
}
